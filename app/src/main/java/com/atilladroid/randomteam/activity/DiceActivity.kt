package com.atilladroid.randomteam.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.atilladroid.randomteam.R
import kotlinx.android.synthetic.main.activity_dice.*

class DiceActivity : AppCompatActivity() {

    var diceSum = 1
    var numberSum = 0
    var numberSign = "+"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)
        setText()


        ib_plus_d.setOnClickListener {
            plusDice()
            setText()
        }
        ib_minus_d.setOnClickListener {
            minusDice()
            setText()
        }
        ib_plus_number.setOnClickListener {
            plusNumber()
            setText()
        }
        ib_minus_number.setOnClickListener {
            minusNumber()
            setText()
        }

        iv_dice_d2.setOnClickListener { dialog(createDice(2)) }
        iv_dice_d3.setOnClickListener { dialog(createDice(3)) }
        iv_dice_d4.setOnClickListener { dialog(createDice(4)) }
        iv_dice_d6.setOnClickListener { dialog(createDice(6)) }
        iv_dice_d8.setOnClickListener { dialog(createDice(8)) }
        iv_dice_d10.setOnClickListener { dialog(createDice(10)) }
        iv_dice_d12.setOnClickListener { dialog(createDice(12)) }
        iv_dice_d20.setOnClickListener { dialog(createDice(20)) }
        iv_dice_d100.setOnClickListener { dialog(createDice(100)) }


    }


    fun setText() {
        tv_dice_sum.text = "${diceSum}d"
        tv_number_sum.text = if (numberSum >= 0) {
            "$numberSign$numberSum"
        } else {
            "$numberSum"
        }
    }

    fun plusDice() {
        if (diceSum == 100) {
        } else {
            diceSum++
        }
    }

    fun minusDice() {
        if (diceSum == 1) {
        } else {
            diceSum--
        }
    }

    fun plusNumber() {
        if (numberSum == 100) {
        } else {
            numberSum++
        }
    }

    fun minusNumber() {
        if (numberSum == -100) {
        } else {
            numberSum--
        }
    }

    fun generateRandom(dice: Int): Int {
        Handler().postDelayed({ }, 3)
        return (Math.random() * dice).toInt() + 1
    }

    fun createDice(dice: Int): MutableList<Int> {
        val listResult = mutableListOf<Int>()
        var result: Int

        for (i in 1..diceSum) {
            listResult.add(generateRandom(dice))
        }
        result = listResult.sum() + numberSum
        listResult.add(result)
        listResult.add(dice)
        return listResult
    }

    private fun dialog(result: MutableList<Int>) {
        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_dice_result)
        val tvNumberDice = dialog.findViewById<TextView>(R.id.tv_number_dice)
        val tvDiceResult = dialog.findViewById<TextView>(R.id.tv_dice_result)
        val tvDiceSmollResult = dialog.findViewById<TextView>(R.id.tv_dice_smoll_result)


        var dice = "${diceSum}d${result[result.lastIndex]}"
        if (numberSum != 0) {
            if (numberSum > 0) {
                dice += "${numberSign}${numberSum}"
            } else {
                dice += "${numberSum}"
            }
        }
        tvNumberDice.text = dice

        tvDiceResult.text = "${result[result.lastIndex - 1]}"
        var smollResult: String = ""
        for (i in 0..result.lastIndex - 2) {
            if (i == 0) {
                smollResult = result[i].toString()
            } else {
                smollResult += ", ${result[i]}"
            }
        }
        tvDiceSmollResult.text = smollResult



        dialog.show()
    }
}

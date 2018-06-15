package com.atilladroid.randomteam.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.atilladroid.randomteam.R
import com.squareup.picasso.Picasso


class DiceSelect : ConstraintLayout {

    private var dice_image: ImageView
    private var minus_d: ImageButton
    private var plus_d: ImageButton
    private var minus_number: ImageButton
    private var plus_number: ImageButton
    private var dice_sum: TextView
    private var number_sum: TextView
    private var diceSum = 0
    private var numberSum = 0
    private var numberSign = "+"


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.dice_select, this, true)

        minus_number = findViewById(R.id.ib_minus_number)
        plus_number = findViewById(R.id.ib_plus_number)
        minus_d = findViewById(R.id.ib_minus_d)
        plus_d = findViewById(R.id.ib_plus_d)
        dice_image = findViewById(R.id.iv_dice_image)
        dice_sum = findViewById(R.id.tv_dice_sum)
        number_sum = findViewById(R.id.tv_number_sum)

        plus_d.setOnClickListener {
            plusDice()
            setText()
        }
        minus_d.setOnClickListener {
            minusDice()
            setText()
        }
        plus_number.setOnClickListener {
            plusNumber()
            setText()
        }
        minus_number.setOnClickListener {
            minusNumber()
            setText()
        }

    }

    fun getDice(): Array<Int> {
        return arrayOf(diceSum, numberSum)
    }

    fun setDice(image: Int, diceSum: Int , numberSum: Int ) {

        this.diceSum = diceSum
        this.numberSum = numberSum

        setText()

        Picasso.get()
                .load(image)
                .into(dice_image)

    }


    fun setText() {
        dice_sum.text = "${diceSum}d"
        number_sum.text = if (numberSum >= 0) {
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
        if (diceSum == 0) {
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


    /*  fun isChecked(): Boolean = switch.isChecked

      fun setChecked(checked: Boolean) = switch.setChecked(checked)

      fun setOnCheckedListener(listner : CompoundButton.OnCheckedChangeListener?)
              = switch.setOnCheckedChangeListener(listner)
  */
}
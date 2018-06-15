package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atilladroid.randomteam.MySettings
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RoundViewModel
import kotlinx.android.synthetic.main.fragment_dice_select.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DiceSelectFragment : Fragment() {

    lateinit var viewModel: RoundViewModel

    private val diceName = arrayOf(
            "d2",
            "d3",
            "d4",
            "d6",
            "d8",
            "d10",
            "d12",
            "d20",
            "d100")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(activity!!).get(RoundViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectDice.observe(this, Observer { select ->
            setDices(select as MySettings)
        })

    }

    override fun onStop() {
        super.onStop()

        val dice = arrayOf(
                DS_d2,
                DS_d3,
                DS_d4,
                DS_d6,
                DS_d8,
                DS_d10,
                DS_d12,
                DS_d20,
                DS_d100)

        val select: MutableMap<String, Array<Int>> = mutableMapOf()

        for (i in 0 until diceName.size) {
            select[diceName[i]] = dice[i].getDice()
        }
        viewModel.saveSelect(select)

    }

    fun setDices(settings: MySettings) {
        val dice = arrayOf(
                DS_d2,
                DS_d3,
                DS_d4,
                DS_d6,
                DS_d8,
                DS_d10,
                DS_d12,
                DS_d20,
                DS_d100)
        val d = arrayOf(
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice6,
                R.drawable.dice8,
                R.drawable.dice10,
                R.drawable.dice12,
                R.drawable.dice20,
                R.drawable.dice100)

        for (i in 0 until d.size) {
            dice[i].setDice(d[i], settings.diceSize[diceName[i]]!!, settings.diceNumber[diceName[i]]!!)
        }
    }

}

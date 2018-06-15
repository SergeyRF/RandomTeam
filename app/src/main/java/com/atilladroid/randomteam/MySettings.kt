package com.atilladroid.randomteam

/**
 * Created by sergey on 5/30/18.
 */
class MySettings() {
    var number: String = ""
    var diceSize = mutableMapOf<String, Int>()
    var diceNumber = mutableMapOf<String, Int>()
    private val dice = arrayOf(
            "d2",
            "d3",
            "d4",
            "d6",
            "d8",
            "d10",
            "d12",
            "d20",
            "d100")

    init {

        for (n in 0 until dice.size) {
            diceSize[dice[n]] = 0
            diceNumber[dice[n]] = 0
        }
    }

    fun setSelect(select: MutableMap<String, Array<Int>>) {

        for (n in 0 until dice.size) {
            val sel = select[dice[n]]
            diceSize[dice[n]] = sel!![0]
            diceNumber[dice[n]] = sel[1]
        }

    }

    fun getDicesNames() = dice


/*
    fun number() = flags.and(2) == 2

    fun dice() = flags.and(4)==4*/

}
package com.atilladroid.randomteam.game

import android.os.Handler
import com.atilladroid.randomteam.MySettings
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import java.util.*

/**
 * Created by sergey on 5/11/18.
 */
object Logika {

    private val players = mutableMapOf<Long, Player>()
    private val teams = mutableListOf<Team>()
    var teamNames: MutableList<String> = mutableListOf()


    fun maxTeamsCount(): Int = players.size

    fun addPlayer(player: Player): Boolean {
        return if (players.containsKey(player.id)) {
            false
        } else {
            players[player.id] = player
            true
        }
    }

    fun removePlayer(player: Player) {
        players.remove(player.id)
    }

    fun reNamePlayer(player: Player) {
        players[player.id]?.name = player.name
    }

    fun getPlayers(): List<Player> {
        return players.values.toList()
    }


    fun createTeams(count: Int) {
        val shuffledPlayers: MutableList<Player> = players.values.toMutableList()
        Collections.shuffle(shuffledPlayers)

        val playersQueue = ArrayDeque<Player>()
        playersQueue.addAll(shuffledPlayers)

        // why not?
        teams.clear()

        Collections.shuffle(teamNames)

        for (i in 0 until count) {
            val teamName = teamNames.getOrElse(i, { _ -> "Team $i" })
            teams.add(Team(teamName))
        }

        var currentTeam = 0
        while (playersQueue.isNotEmpty()) {
            teams[currentTeam].players.add(playersQueue.remove())

            currentTeam = if (currentTeam >= teams.size - 1) 0 else currentTeam + 1
        }
    }

    fun getTeams(): List<Team> = teams

    fun newGame() {
        players.clear()
        teams.clear()
    }

    fun rollAnyDices(dices: MySettings): Array<MutableList<Int>> {

        val i = dices.getDicesNames()
        var n = 0
        var lenght = 0
        for (f in 0..i.size - 1) {
            if (dices.diceSize[i[f]]!! > 0) {
                lenght++
            }
        }
        var diceResultEvery: Array<MutableList<Int>> = Array(lenght, { mutableListOf<Int>() })


        i.forEach {
            if (dices.diceSize[it]!! > 0) {

                diceResultEvery.set(n,
                        createDice(dices.diceSize[it]!!.toInt(), dices.diceNumber[it]!!.toInt(),
                                it.removePrefix("d").toInt()))
                n++
            }
        }

        return diceResultEvery
    }

    fun createDice(diceSum: Int, numberSum: Int, dice: Int): MutableList<Int> {
        val listResult = mutableListOf<Int>()
        var result: Int

        for (i in 1..diceSum) {
            listResult.add(generateRandom(dice))
        }
        result = listResult.sum() + numberSum
        listResult.add(result)
        listResult.add(dice)
        listResult.add(numberSum)
        listResult.add(diceSum)
        //результаты бросков,..., общий результат, кубик, добавка, кол-во кубиков
        return listResult
    }

    fun generateRandom(dice: Int): Int {
        Handler().postDelayed({ }, dice.toLong() / 10)
        return (Math.random() * dice).toInt() + 1
    }
}
package com.atilladroid.randomteam.beans

/**
 * Created by sergey on 5/11/18.
 */
class Team(var name: String, var players : MutableList<Player> = mutableListOf()) {


    var id = -1

    var currentPlayer = -1

    fun getPlayer() : Player {
        if (currentPlayer==-1){
            currentPlayer++
        }
       return players[currentPlayer]
    }

    fun nextPlayer() : Player {
        currentPlayer++
        if (currentPlayer >= players.size) currentPlayer = 0
        return players[currentPlayer]
    }

    override fun toString(): String {
        return "Team $name players: $players"
    }
}
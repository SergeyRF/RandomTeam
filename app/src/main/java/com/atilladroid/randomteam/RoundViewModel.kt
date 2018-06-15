package com.atilladroid.randomteam

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.game.Logika
import timber.log.Timber

/**
 * Created by sergey on 5/12/18.
 */
class RoundViewModel(application: Application) : AndroidViewModel(application) {

    var currentTeam = MutableLiveData<Team>()
    var currentPlayer = MutableLiveData<Player>()
    private val teams: List<Team> = Logika.getTeams()
    var currentTeamPosition = 0
    var selectDice = MutableLiveData<MySettings>()
    val playerSettings = mutableMapOf<String, MySettings>()

    init {
        updateData()
        createSettings()

    }

    fun getPlayerNumber() = playerSettings[currentPlayer.value?.name]?.number
    fun setPlayerNumber(i: String) {
        Timber.d("Set player number $i")
        playerSettings[currentPlayer.value!!.name]!!.number = i
    }


    fun createSettings() {
        for (i in 0 until teams.size) {
            teams[i].players.forEach { playerSettings[it.name] = MySettings() }
        }
        updateData()
    }

    fun haveSelectDice(): Boolean {
        selectDice.value!!.diceSize.forEach {
            if (it.value > 0) {
                return true
            }
        }
        return false
    }

    fun saveSelect(select: MutableMap<String, Array<Int>>) {
        playerSettings[currentPlayer.value!!.name]!!.setSelect(select)
    }

    fun rollDices(): Array<MutableList<Int>> {
        return Logika.rollAnyDices(selectDice.value!!)
    }


    fun nextTeam() {
        if (currentTeamPosition == teams.size - 1) {
            currentTeamPosition = 0

        } else {
            currentTeamPosition++
        }

        teams[currentTeamPosition].nextPlayer()
        updateData()
    }

    fun setSettingsPlayer(flag: Int) {
        currentPlayer.value?.settings = flag
    }

    fun setSettingsEvery(flag: Int) {
        Logika.getPlayers().forEach { it.settings = flag }
    }


    fun updateData() {
        currentPlayer.value = teams[currentTeamPosition].getPlayer()
        currentTeam.value = teams[currentTeamPosition]
        selectDice.value = playerSettings[currentPlayer.value!!.name]

    }


}
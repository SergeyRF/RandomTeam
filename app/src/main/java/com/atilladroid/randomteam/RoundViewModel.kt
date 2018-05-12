package com.atilladroid.randomteam

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.game.Logika

/**
 * Created by sergey on 5/12/18.
 */
class RoundViewModel(application: Application) : AndroidViewModel(application) {

    var currentTeam = MutableLiveData<Team>()
    var currentPlayer = MutableLiveData<Player>()
    private val teams: List<Team> = Logika.getTeams()
    var currentTeamPosition = 0

    init {
        updateData()
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


    fun updateData() {
        currentPlayer.value = teams[currentTeamPosition].getPlayer()
        currentTeam.value = teams[currentTeamPosition]
    }


}
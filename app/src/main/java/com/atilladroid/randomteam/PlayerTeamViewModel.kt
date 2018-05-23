package com.atilladroid.randomteam

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.widget.Toast
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.db.DataProvider
import com.atilladroid.randomteam.game.Logika
import com.atilladroid.randomteam.game.PlayerType
import com.atilladroid.randomteam.utils.SingleLiveEvent
import com.atilladroid.randomteam.utils.random
import timber.log.Timber

/**
 * Created by sergey on 5/11/18.
 */
class PlayerTeamViewModel(application: Application) : AndroidViewModel(application) {

    val StartFragmentLD = SingleLiveEvent<FragmentStart>()

    private val playersLiveData = MutableLiveData<List<Player>>()
    private val teamsLiveData = MutableLiveData<List<Team>>()
    private val avatarLiveData = MutableLiveData<String>()

    val listOfAvatars: MutableList<String> = mutableListOf()
    private val dataProvider = DataProvider(application)


    init {
        updateData()
    }

    fun getPlayersLiveData(): LiveData<List<Player>> = playersLiveData

    fun getTeamsLiveData(): LiveData<List<Team>> = teamsLiveData

    fun getAvatarLiveData(): LiveData<String> {
        if (listOfAvatars.isEmpty()) {
            AsyncTask.execute {
                synchronized(this, { loadAvatars() })
            }
        }

        return avatarLiveData
    }

    //todo do not add players with the same name
    fun addPlayer(player: Player): Boolean {
        player.id = dataProvider.insertPlayer(player)
        val success = Logika.addPlayer(player)

        if (success) {
            avatarLiveData.value = listOfAvatars.random()
            updateData()
        }

        return success
    }

    fun removePlayer(player: Player) {
        Logika.removePlayer(player)
        updateData()
    }

    fun reNamePlayer(player: Player) {
        if (player.type == PlayerType.USER) {
            Logika.reNamePlayer(player)
        } else {
            Logika.removePlayer(player)
            //set id to zero before inserting
            player.type = PlayerType.USER
            player.id = 0
            player.id = dataProvider.insertPlayer(player)
            Logika.addPlayer(player)
        }
        Timber.d("Players rename name ${player.name} id ${player.id} players size ${Logika.getPlayers().size}")
//        updateData()
    }

    fun addRandomPlayer() {
        //Todo replace this ugly code
        val playersList = dataProvider.getPlayers()
        Timber.d("Player List ${playersList.size}")
        val player: Player? = playersList.find { !Logika.getPlayers().contains(it) }
        if (player != null) Logika.addPlayer(player)

        updateData()
    }

    private fun loadAvatars() {
        listOfAvatars.addAll(dataProvider.getListOfAvatars())
        avatarLiveData.postValue(listOfAvatars.random())
    }

    fun startTeam(){
        StartFragmentLD.value = FragmentStart.START_TEAM
    }

    fun startRound(){
        StartFragmentLD.value = FragmentStart.START_ROUND
    }

    fun initTeams() {
        Logika.createTeams(2)
        updateData()
    }

    fun addTeam() {
        val teamsCount = Logika.getTeams().size + 1
        if (teamsCount <= Logika.maxTeamsCount()) {
            Logika.createTeams(teamsCount)
            updateData()
        } else {
            Toast.makeText(getApplication(), R.string.cant_create_teams, Toast.LENGTH_SHORT).show()
        }
    }

    fun reduceTeam() {
        if (Logika.getTeams().size > 2) {
            val teamsCount = Logika.getTeams().size - 1
            Logika.createTeams(teamsCount)
            updateData()
        } else {
            Toast.makeText(getApplication(), R.string.two_team_min, Toast.LENGTH_SHORT).show()
        }
    }

    fun shuffleTeams() {
        Logika.createTeams(Logika.getTeams().size)
        updateData()
    }

    fun newGame(){
        Logika.newGame()
        updateData()
    }


    private fun updateData() {
        playersLiveData.value = Logika.getPlayers()
        teamsLiveData.value = Logika.getTeams()
    }

    enum class FragmentStart{
        START_PLAYER,
        START_TEAM,
        START_ROUND
    }
}
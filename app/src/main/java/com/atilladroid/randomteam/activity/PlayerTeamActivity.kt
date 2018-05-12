package com.atilladroid.randomteam.activity

import android.app.FragmentTransaction
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.activity.fragment.PlayerFragment
import com.atilladroid.randomteam.activity.fragment.RoundFragment
import com.atilladroid.randomteam.activity.fragment.TeamFragment

class PlayerTeamActivity : AppCompatActivity() {
    lateinit var viewModel: PlayerTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_team)
        viewModel = ViewModelProviders.of(this).get(PlayerTeamViewModel::class.java)
        viewModel.StartFragmentLD.observe(this, Observer { start ->
            if (start!=null) onStartFragment(start)
             })

        if (supportFragmentManager.findFragmentById(android.R.id.content) == null) {
            startPlayersFragment()
        }
    }

    fun onStartFragment(start: PlayerTeamViewModel.FragmentStart){
        when(start){
            PlayerTeamViewModel.FragmentStart.START_PLAYER ->startPlayersFragment()
            PlayerTeamViewModel.FragmentStart.START_TEAM ->startTeamsFragment()
            PlayerTeamViewModel.FragmentStart.START_ROUND ->startRoundFragment()
        }
    }

    private fun startPlayersFragment() {
        val fragment = PlayerFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
    }

    private fun startTeamsFragment() {
        val fragment = TeamFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    private fun startRoundFragment(){
        val fragment = RoundFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }
}

package com.atilladroid.randomteam.activity

import android.app.FragmentTransaction
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.activity.fragment.PlayerFragment
import com.atilladroid.randomteam.activity.fragment.RoundFragment
import com.atilladroid.randomteam.activity.fragment.TeamFragment

class PlayerTeamActivity : AppCompatActivity() {
    lateinit var viewModel: PlayerTeamViewModel

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_team)
        viewModel = ViewModelProviders.of(this).get(PlayerTeamViewModel::class.java)
        viewModel.StartFragmentLD.observe(this, Observer { start ->
            if (start != null) onStartFragment(start)
        })

        if (supportFragmentManager.findFragmentById(android.R.id.content) == null) {
            startPlayersFragment()
        }
    }

    fun onStartFragment(start: PlayerTeamViewModel.FragmentStart) {
        when (start) {
            PlayerTeamViewModel.FragmentStart.START_PLAYER -> startPlayersFragment()
            PlayerTeamViewModel.FragmentStart.START_TEAM -> startTeamsFragment()
            PlayerTeamViewModel.FragmentStart.START_ROUND -> startRoundFragment()
        }
    }

    private fun startPlayersFragment() {
        viewModel.newGame()
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

    private fun startRoundFragment() {
        val fragment = RoundFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager.findFragmentById(android.R.id.content)
        if (fragment is RoundFragment) {
            if (backPressedOnce) {
                finish()
            } else {
                backPressedOnce = true
                Toast.makeText(this, R.string.press_more_to_finish_game, Toast.LENGTH_LONG).show()
                Handler().postDelayed({ backPressedOnce = false }, 2000)
            }
        } else {
            super.onBackPressed()
        }
    }
}

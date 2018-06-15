package com.atilladroid.randomteam.activity

import android.app.FragmentTransaction
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.activity.fragment.*

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
            PlayerTeamViewModel.FragmentStart.START_HINT -> startHintTeam()
            PlayerTeamViewModel.FragmentStart.START_DICE -> startDice()
            PlayerTeamViewModel.FragmentStart.START_SETTINGS -> startSettings()
            PlayerTeamViewModel.FragmentStart.START_DICE_SELECT -> startDiceSelect()
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

    private fun startDiceSelect() {
        val fragment = DiceSelectFragment()
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

    private fun startHintTeam() {
        val fragment = HintTeamFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    private fun startDice() {
        startActivity(Intent(this, DiceActivity::class.java))
    }

    private fun startSettings() {
        val fragment = SettingsFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    private fun menuBackPressed(b: Boolean) {
        if (true) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
        /* else{
             supportActionBar?.setDisplayHomeAsUpEnabled(false)
             supportActionBar?.setHomeButtonEnabled(false)
         }*/
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (item.itemId) {
                android.R.id.home -> onBackPressed()
                R.id.item_show_hint -> startHintTeam()
                R.id.item_settings -> startSettings()
            }
        }

        return true
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

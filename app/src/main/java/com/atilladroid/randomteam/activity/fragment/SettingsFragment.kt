package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RoundViewModel
import com.atilladroid.randomteam.beans.Player
import kotlinx.android.synthetic.main.fragment_settings.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    lateinit var viewModelTeam: RoundViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModelTeam = ViewModelProviders.of(activity!!).get(RoundViewModel::class.java)
        return inflater.inflate(R.layout.fragment_settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelTeam.currentPlayer.observe(this, Observer { player ->
            setSwitch(player as Player)
        })

    }

    private fun setSwitch(player: Player) {
        if (player.settings.and(2) == 2) {
            sw_number_show.isChecked = true
        }
        if (player.settings.and(4) == 4) {
            sw_dises_show.isChecked = true
        }
    }


    override fun onStop() {
        super.onStop()
        var i = 0

        if (sw_number_show.isChecked) {
            i =  i.or(2)
        } else {
            if (i.and(2)==2) {
                i = i.xor(2)
            }
        }

         if (sw_dises_show.isChecked) {
             i =  i.or(4)
        } else {
            if (i.and(4)==4) {
                i = i.xor(4)
            }
        }
        if (sw_for_every.isChecked) {
            viewModelTeam.setSettingsEvery(i)
        } else viewModelTeam.setSettingsPlayer(i)

    }

}// Required empty public constructor

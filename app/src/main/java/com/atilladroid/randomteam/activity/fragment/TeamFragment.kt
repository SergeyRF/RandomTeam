package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.PrecaheLayoutManager
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RvAdapter
import com.atilladroid.randomteam.beans.Team
import kotlinx.android.synthetic.main.fragment_team.*


/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment() {

    lateinit var viewModel: PlayerTeamViewModel
    lateinit var adapterTeam: RvAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(PlayerTeamViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTeamsLiveData().observe(this, Observer { list -> setTeemRv(list) })
        viewModel.initTeams()

         bt_start_round.setOnClickListener {
             viewModel.startRound()
         }

        adapterTeam = RvAdapter()
        val layoutManager = PrecaheLayoutManager(context)
        rvTeams.layoutManager = layoutManager

        rvTeams.adapter = adapterTeam

        /* adapterTeam.listener = { team: Any ->
             dialog(team as Team)
         }*/

        fabAddTeam.setOnClickListener {
            viewModel.addTeam()
        }

        fabRemoveTeam.setOnClickListener {
            viewModel.reduceTeam()
        }

        fabShuffleTeams.setOnClickListener {
            viewModel.shuffleTeams()
        }
    }

    private fun setTeemRv(teem: List<Team>?) {
        adapterTeam.setData(teem)
    }

}// Required empty public constructor

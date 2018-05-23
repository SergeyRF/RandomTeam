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
import kotlinx.android.synthetic.main.fragment_hint_team.*


/**
 * A simple [Fragment] subclass.
 */
class HintTeamFragment : Fragment() {

    val adapter = RvAdapter()

    lateinit var viewModel: PlayerTeamViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hint_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(PlayerTeamViewModel::class.java)

        val layoutManager = PrecaheLayoutManager(context)

        // rvHintTeam_and_Result.layoutManager = LinearLayoutManager(view.context)
        rvHintTeam_and_Result.layoutManager = layoutManager
        rvHintTeam_and_Result.adapter = adapter
        adapter.teamRename = false
        viewModel.getTeamsLiveData().observe(this, Observer { list -> adapter.setData(list as List<Team>) })
        // adapter.setData(viewModel.getTeamsLiveData().value)


    }

}// Required empty public constructor

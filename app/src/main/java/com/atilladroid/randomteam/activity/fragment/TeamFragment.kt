package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.PrecaheLayoutManager
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RvAdapter
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.utils.Functions
import com.takusemba.spotlight.SimpleTarget
import com.takusemba.spotlight.Spotlight
import kotlinx.android.synthetic.main.fragment_team.*


/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment() {

    lateinit var viewModel: PlayerTeamViewModel
    lateinit var adapterTeam: RvAdapter

    companion object {
        const val PLAYING_HINT = "playing_hint"
    }

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

    override fun onStart() {
        super.onStart()
        //show hint if first game
        globalListentrForSpotl()
    }

    fun spotl() {

        val shaffleTeam = SimpleTarget.Builder(activity!!)
                .setPoint(floatingMenu.menuIconView)
                .setRadius(80f)
                .setTitle(getString(R.string.hint_team_shaffle))
                .setDescription(getString(R.string.hint_team_shaffle_button))

                .build()

        //Position for rename icon
        val x = Functions.getScreenWidth(activity!!) - 40
        val y = 40F + Functions.dpToPx(context!!, 72F)
        val renameGuide = SimpleTarget.Builder(activity!!)
                .setPoint(x.toFloat(), y)
                .setRadius(40f)
                .setTitle(getString(R.string.rename))
                .setDescription(getString(R.string.click_to_rename))
                .build()


        Spotlight.with(activity!!)
                .setOverlayColor(ContextCompat.getColor(activity!!, R.color.anotherBlack))
                .setDuration(100L)
                .setTargets(shaffleTeam, renameGuide)
                .setClosedOnTouchedOutside(true)
                .setAnimation(DecelerateInterpolator(2f))
                .start()

    }

    private fun globalListentrForSpotl() {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        if (preference.getBoolean(TeamFragment.PLAYING_HINT, true)) {

            view!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    spotl()
                    editor.putBoolean(TeamFragment.PLAYING_HINT, false).apply()
                }
            })
        }
    }

}// Required empty public constructor

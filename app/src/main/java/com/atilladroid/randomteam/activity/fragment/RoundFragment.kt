package com.atilladroid.randomteam.activity.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.*
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RoundViewModel
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.utils.Functions
import com.atilladroid.randomteam.utils.gone
import com.atilladroid.randomteam.utils.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_round.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoundFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RoundFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoundFragment : Fragment() {

    lateinit var viewModel: RoundViewModel
    lateinit var viewModelTeam: PlayerTeamViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(RoundViewModel::class.java)
        viewModelTeam = ViewModelProviders.of(activity!!).get(PlayerTeamViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.currentTeam.observe(this, Observer { team ->
            setTeam(team as Team)
        })
        viewModel.currentPlayer.observe(this, Observer { player ->
            setPlayer(player as Player)
        })
        bt_next_player.setOnClickListener {
            viewModel.setPlayerNumber(et_my_number.text.toString())
            viewModel.nextTeam()

            et_my_number.setText(viewModel.getPlayerNumber())
            et_my_number.inputType = InputType.TYPE_CLASS_NUMBER

        }
        tv_TurnTeamName.setOnClickListener {
            viewModelTeam.startHint()
        }

        civDice.setOnClickListener {
            viewModelTeam.startDice()
        }

        et_my_number.inputType = InputType.TYPE_CLASS_NUMBER


    }

    fun setTeam(team: Team) {
        tv_TurnTeamName.text = team.name
    }

    fun setPlayer(player: Player) {
        tvTurnPlayerName.text = player.name
        Picasso.get()
                .load(Functions.imageNameToUrl("player_avatars/large/${player.avatar}"))
                .into(civPlayerAvatar)
        showHideFlags(player)
    }

    fun showHideFlags(player: Player) {
        val flag: Int = player.settings
        if (flag.and(2) == 2) et_my_number.show() else et_my_number.gone()
        if (flag.and(4) == 4) civ_my_dice.show() else civ_my_dice.gone()
    }

    fun showHideFlags() {
        val flag: Int = viewModel.currentPlayer.value!!.settings
        if (flag.and(2) == 2) et_my_number.show() else et_my_number.gone()
        if (flag.and(4) == 4) civ_my_dice.show() else civ_my_dice.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_hint, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         Timber.d("${item?.itemId}")
         if (item?.itemId == R.id.item_show_hint) {
             Timber.d("Click Show Hint")
             viewModelTeam.startHint()
             return true
         }
         if(item?.itemId==R.id.item_settings){
             Timber.d("Click Settings")
             viewModelTeam.startSettings()
             return true
         }
         return super.onOptionsItemSelected(item)
     }*/

}// Required empty public constructor

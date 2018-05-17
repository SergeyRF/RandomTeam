package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RvAdapter
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.utils.Functions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_player.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    lateinit var adapter: RvAdapter
    lateinit var viewModel: PlayerTeamViewModel
    private var avatar: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(activity!!).get(PlayerTeamViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linLayout = LinearLayoutManager(context)
        linLayout.stackFromEnd = true
        linLayout.reverseLayout = true

        rvPlayers.layoutManager = linLayout
        adapter = RvAdapter()
        rvPlayers.adapter = adapter

        viewModel.getPlayersLiveData().observe(this, Observer { list -> onPlayersChanged(list) })


        adapter.listener = { player: Any ->
            //todo player saves incorrect
            viewModel.reNamePlayer(player as Player)
        }

        adapter.listenerTwo = { player: Any ->

            viewModel.removePlayer(player as Player)

        }

        imageButton.setOnClickListener {
            addPlayer()
        }


        bt_GoToTeam.setOnClickListener {
            Timber.d("button next clicked")
            viewModel.startTeam()
        }
        btAddRandomPlayer.setOnClickListener {
            Timber.d("button clicked")
            viewModel.addRandomPlayer()
        }

        etName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // обработка нажатия Enter
                addPlayer()
                true
            } else true
        }

        viewModel.getAvatarLiveData().observe(this, Observer { avatar ->
            avatar?.let { showAvatar(it) }
        })

    }


    private fun onPlayersChanged(players: List<Player>?) {
        adapter.setData(players)
        val position = players?.size ?: 0
        rvPlayers.scrollToPosition(position - 1)
        Timber.d("Add random player $position")
    }
    private fun addPlayer() {
        if (etName.text.isNotEmpty()) {
            val newPlayer = Player(etName.text.toString().trim())

            if (avatar != null) {
                newPlayer.avatar = avatar!!
            }

            if (viewModel.addPlayer(newPlayer)) {
                etName.text.clear()
            } else {
                Toast.makeText(context, R.string.name_not_unic, Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, R.string.player_name_empty, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAvatar(fileName: String) {
        avatar = fileName
        val fileLink = Functions.imageNameToUrl("player_avatars/small/$fileName")
        Picasso.get()
                .load(fileLink)
                .into(civPlayerAvatar)
    }

}// Required empty public constructor

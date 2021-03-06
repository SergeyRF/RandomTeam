package com.atilladroid.randomteam.activity.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.atilladroid.randomteam.PlayerTeamViewModel
import com.atilladroid.randomteam.R
import com.atilladroid.randomteam.RvAdapter
import com.atilladroid.randomteam.activity.dialog.AvatarSelectDialog
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.utils.Functions
import com.squareup.picasso.Picasso
import com.takusemba.spotlight.SimpleTarget
import com.takusemba.spotlight.Spotlight
import kotlinx.android.synthetic.main.fragment_player.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    lateinit var adapter: RvAdapter
    lateinit var viewModel: PlayerTeamViewModel
    private var avatar: String? = null

    companion object {
        const val SHOW_SPOTLIGHT = "spotlight_show"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        viewModel = ViewModelProviders.of(activity!!).get(PlayerTeamViewModel::class.java)

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
            if (viewModel.noNeedPlayer()) {
                viewModel.startTeam()
            } else {
                Toast.makeText(context, R.string.not_enough_players, Toast.LENGTH_LONG).show()
            }
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

        civPlayerAvatar.setOnClickListener {
            val dialog = AvatarSelectDialog(context!!, viewModel.listOfAvatars)
            dialog.onSelect = dialogOnSelect
            dialog.show()
        }

    }

    val dialogOnSelect: (String) -> Unit = { fileName ->
        Timber.d("avatar select $fileName")
        showAvatar(fileName)
    }

    override fun onStart() {
        super.onStart()
        globalListentrForSpotl()
    }

    private fun globalListentrForSpotl() {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        if (preference.getBoolean(SHOW_SPOTLIGHT, true)) {

            view!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    Timber.d("On global changed")
                    view!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    spotl()
                    editor.putBoolean(SHOW_SPOTLIGHT, false).apply()
                }
            })
        }
    }

    fun spotl() {

        val custom = SimpleTarget.Builder(activity!!)
                .setPoint(btAddRandomPlayer)
                .setRadius(80f)
                .setTitle(getString(R.string.hint_random_player))
                .setDescription(getString(R.string.hint_inject_random_player))
                /* .setOnSpotlightStartedListener(object : OnTargetStateChangedListener<SimpleTarget> {

                     override fun onStarted(target: SimpleTarget?) {
                     }

                     override fun onEnded(target: SimpleTarget?) {
                     }
                 })*/
                .build()
        val injectName = SimpleTarget.Builder(activity!!)
                .setRadius(80f)
                .setPoint(imageButton)
                .setTitle(getString(R.string.hint_inject_name))
                .setDescription(getString(R.string.hint_inject_name_button))
                .build()

        Spotlight.with(activity!!)
                .setOverlayColor(ContextCompat.getColor(activity!!, R.color.anotherBlack))
                .setDuration(300L)
                .setTargets(injectName, custom)
                .setClosedOnTouchedOutside(true)
                .setAnimation(DecelerateInterpolator(2f))
                .start()

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

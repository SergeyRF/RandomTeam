package com.atilladroid.randomteam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team
import com.atilladroid.randomteam.utils.Functions
import com.atilladroid.randomteam.utils.gone
import com.atilladroid.randomteam.utils.hide
import com.atilladroid.randomteam.utils.show
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber

/**
 * Created by sergey on 5/11/18.
 */
abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    var listener: ((Any) -> Unit)? = null
    var listenerTwo: ((Any) -> Unit)? = null
    var listenerThree: ((Any) -> Unit)? = null
}

class PlayerHolder(val view: View) : BaseHolder(view) {
    val tvName: TextView = view.findViewById(R.id.wordInject)
    val etName: EditText = view.findViewById(R.id.etRename)
    val avatarImage: CircleImageView = view.findViewById(R.id.civPlayerAvatar)
    val delPlayer: ImageButton = view.findViewById(R.id.ib_delPlayer)

    fun bind(player: Player) {
        tvName.show()
        etName.hide()
        delPlayer.hide()
        tvName.text = player.name

        itemView.setOnClickListener {
            tvName.hide()
            etName.show()
            delPlayer.show()
            etName.setText("")
            etName.append(player.name)
            etName.requestFocus()
            Functions.showKeyboard(view.context, etName)
        }

        delPlayer.setOnClickListener {
            listenerTwo?.invoke(player)
        }

        etName.setOnFocusChangeListener { view, hasFocus ->
            Timber.d(" focus changed$hasFocus")
            if (!hasFocus) {
                if (etName.text.isNotEmpty() && etName.text.toString() != player.name) {
                    player.name = etName.text.toString()
                    tvName.text = player.name

                    listener?.invoke(player)
                }

                tvName.show()
                etName.hide()
                delPlayer.hide()
            }
        }

        Picasso.get()
                .load(Functions.imageNameToUrl("player_avatars/small/${player.avatar}"))
                .into(avatarImage)
    }

}

class TeamHolder(val view: View) : BaseHolder(view) {
    val teamName: TextView = view.findViewById(R.id.tvTeamName)
    val playersList: LinearLayout = view.findViewById(R.id.llPlayers)
    val ivRename = view.findViewById<ImageButton>(R.id.ibTeemRename)

    fun bind(team: Team) {
        teamName.text = team.name

        playersList.removeAllViews()
        for (player in team.players) {
            Timber.d("add player into listview")
            val playerView = LayoutInflater.from(view.context)
                    .inflate(R.layout.holder_player_inteam, playersList, false)

            val holder = PlayerInTeamHolder(playerView)
            holder.bind(player)

            playersList.addView(playerView)
        }


        ivRename.setOnClickListener {
            listener?.invoke(team)
        }

        teamName.setOnLongClickListener {
            listener?.invoke(team)
            true
        }
    }
}

class PlayerInTeamHolder(view: View) : BaseHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tvName)
    val tvScores: TextView = view.findViewById(R.id.tvScores)
    val avatarImage: CircleImageView = view.findViewById(R.id.civPlayerAvatar)

    fun bind(player: Player, scores: Int = Int.MIN_VALUE) {
        tvName.text = player.name
        if (scores != Int.MIN_VALUE) {
            tvScores.show()
            tvScores.text = scores.toString()
        } else {
            tvScores.hide()
        }

        Picasso.get()
                .load(Functions.imageNameToUrl("player_avatars/small/${player.avatar}"))
                .into(avatarImage)
    }
}

class TeamHolderWithoutRename(val view: View) : BaseHolder(view) {
    val teamName: TextView = view.findViewById(R.id.tvTeamName)
    val playersList: LinearLayout = view.findViewById(R.id.llPlayers)
    val ivRename = view.findViewById<ImageButton>(R.id.ibTeemRename)

    fun bind(team: Team) {
        teamName.text = team.name

        playersList.removeAllViews()
        for (player in team.players) {
            Timber.d("add player into listview")
            val playerView = LayoutInflater.from(view.context)
                    .inflate(R.layout.holder_player_inteam, playersList, false)

            val holder = PlayerInTeamHolder(playerView)
            holder.bind(player)

            playersList.addView(playerView)
        }

        ivRename.gone()


        teamName.setOnLongClickListener {
            listener?.invoke(team)
            true
        }
    }
}

class DiceResultHolder(view: View) : BaseHolder(view) {

    val tvDiceName: TextView = view.findViewById(R.id.tv_diceName)
    val tvDiceResult: TextView = view.findViewById(R.id.tv_diceResult)
    val tvDiceSmollResult: TextView = view.findViewById(R.id.tv_diceSmollResult)

    fun bind(dices: MutableList<Int>) {
//результаты бросков,..., общий результат, кубик, добавка, кол-во кубиков


        val diceSum = dices[dices.lastIndex]
        val numberSum = dices[dices.lastIndex - 1]
        val diceName = dices[dices.lastIndex - 2]
        val result = dices[dices.lastIndex - 3]


        var dice = "${diceSum}d${diceName}"
        if (numberSum != 0) {
            if (numberSum > 0) {
                dice += "+${numberSum} = "
            } else {
                dice += "${numberSum} = "
            }
        } else dice += " = "
        tvDiceName.text = dice

        tvDiceResult.text = "${result}"
        var smollResult: String = ""
        for (i in 0..dices.lastIndex - 4) {
            if (i == 0) {
                smollResult = dices[i].toString()
            } else {
                smollResult += ", ${dices[i]}"
            }
        }
        tvDiceSmollResult.text = smollResult
    }
}
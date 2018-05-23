package com.atilladroid.randomteam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atilladroid.randomteam.beans.Player
import com.atilladroid.randomteam.beans.Team

/**
 * Created by sergey on 5/11/18.
 */
class RvAdapter : RecyclerView.Adapter<BaseHolder>() {


    private var data: List<Any>? = null

    var listener: ((Any) -> Unit)? = null
    var listenerTwo: ((Any) -> Unit)? = null
    var listenerThree: ((Any) -> Unit)? = null

    var teamRename = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        val holder = when (viewType) {
            VIEW_TYPE_PLAYER -> PlayerHolder(view)
            VIEW_TYPE_TEAM -> if (teamRename) TeamHolder(view) else TeamHolderWithoutRename(view)
            else -> throw RuntimeException("Unsupported item type")
        }

        holder.listener = listener
        holder.listenerTwo = listenerTwo
        holder.listenerThree = listenerThree
        return holder

    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item = data!![position]
        when (holder) {
            is PlayerHolder -> holder.bind(item as Player)
            is TeamHolder -> holder.bind(item as Team)
            is TeamHolderWithoutRename -> holder.bind(item as Team)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = data!![position]
        return when (item) {
            is Player -> VIEW_TYPE_PLAYER
            is Team -> VIEW_TYPE_TEAM
            else -> throw RuntimeException("Unsupported item type")
        }
    }

    fun setData(list: List<Any>?) {
        data = list
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_PLAYER = R.layout.holder_player
        const val VIEW_TYPE_TEAM = R.layout.holder_team
    }

}


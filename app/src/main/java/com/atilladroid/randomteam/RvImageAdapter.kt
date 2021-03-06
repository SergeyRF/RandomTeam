package com.atilladroid.randomteam

/**
 * Created by sergey on 5/23/18.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.atilladroid.randomteam.utils.Functions
import com.squareup.picasso.Picasso

class RvImageAdapter() : RecyclerView.Adapter<ImageHolder>() {

    private var data: List<String>? = null

    var listener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(data!![position])
        holder.listener = listener
    }

    fun setData(list: List<String>?) {
        data = list
        notifyDataSetChanged()
    }
}

class ImageHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val iv: ImageView = view.findViewById(R.id.ivImageDialog)

    var listener: ((String) -> Unit)? = null

    fun bind(imageName: String) {
        val link = Functions.imageNameToUrl("player_avatars/small/$imageName")
        Picasso.get()
                .load(link)
                .into(iv)

        view.setOnClickListener { listener?.invoke(imageName) }
    }
}
package com.atilladroid.randomteam.utils

import android.view.View
import java.util.*

/**
 * Created by sergey on 5/11/18.
 */
fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Returns a random element.
 */
fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null
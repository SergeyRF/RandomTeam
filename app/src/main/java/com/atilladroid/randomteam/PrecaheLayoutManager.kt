package com.atilladroid.randomteam

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import timber.log.Timber
import java.lang.IndexOutOfBoundsException

/**
 * Created by sergey on 5/12/18.
 */
class PrecaheLayoutManager(context : Context?) : LinearLayoutManager(context) {

    var extraSpace = 600

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return extraSpace
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (ex : IndexOutOfBoundsException) {
            Timber.e(ex)
        }
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
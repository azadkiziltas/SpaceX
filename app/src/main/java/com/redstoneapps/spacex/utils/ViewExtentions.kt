package com.redstoneapps.spacex.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

fun ImageView.loadFromUrl(url: String?){

    url?.let {
        Glide.with(this.context)
            .load(url)
            // .error(R.drawable.interneticon)
            .fitCenter()
            .into(this)
    }

}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
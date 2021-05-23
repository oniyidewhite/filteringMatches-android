package com.oblessing.filteringmatches.core

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.oblessing.filteringmatches.BuildConfig


private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.loadUrl(string: String) {
    Glide.with(context)
        .asGif()
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .load(string)
        .centerCrop()
        .into(object : ImageViewTarget<GifDrawable?>(this) {
            override fun setResource(resource: GifDrawable?) {
                setImageDrawable(resource)
            }
        })
}

fun debug(action: () -> Unit) {
    if (BuildConfig.DEBUG) action()
}

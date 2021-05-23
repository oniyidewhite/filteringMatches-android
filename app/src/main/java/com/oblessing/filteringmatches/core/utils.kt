package com.oblessing.filteringmatches.core

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.signature.ObjectKey
import com.oblessing.filteringmatches.BuildConfig
import com.oblessing.filteringmatches.R


private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.loadUrl(string: String) {
    Glide.with(context)
        .asGif()
        .signature(ObjectKey(System.currentTimeMillis() / 24*60*60*1000))
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

fun informationDialog(
    context: Context,
    header: String,
    msg: String,
    callback: () -> Unit
): AlertDialog {
    return AlertDialog.Builder(context)
        .setMessage(msg)
        .setTitle(header)
        .setCancelable(false)
        .setPositiveButton(R.string.label_ok) { dialog, _ ->
            dialog.dismiss()
            callback.invoke()
        }
        .create()
}

package com.android.moviewdemo.utils.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load

object ImageViewBindingAdapter {

    @BindingAdapter("setImageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url:String) {
        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        view.load(url){
            crossfade(true)
            placeholder(circularProgressDrawable)
        }
    }
}
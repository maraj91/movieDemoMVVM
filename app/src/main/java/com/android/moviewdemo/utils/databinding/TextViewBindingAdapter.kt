package com.android.moviewdemo.utils.databinding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
object TextViewBindingAdapter {

    @BindingAdapter("setRating")
    @JvmStatic
    fun setRating(view: TextView, ratings:List<Int>) {
        var avgRating = 0
        for (rating in ratings){
            avgRating+=rating
        }
        view.text = "${avgRating/ratings.size}"
    }
}
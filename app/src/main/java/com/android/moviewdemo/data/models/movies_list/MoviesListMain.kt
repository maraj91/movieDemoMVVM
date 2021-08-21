package com.android.moviewdemo.data.models.movies_list


import com.google.gson.annotations.SerializedName

data class MoviesListMain(
    @SerializedName("movies")
    val movies: List<MoviesData>
)
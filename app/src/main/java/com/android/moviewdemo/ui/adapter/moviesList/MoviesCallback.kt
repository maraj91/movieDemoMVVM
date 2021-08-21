package com.android.moviewdemo.ui.adapter.moviesList

import com.android.moviewdemo.data.models.movies_list.MoviesData

interface MoviesCallback {
    fun onMoviesClick(movie:MoviesData)
}
package com.android.moviewdemo.data.remote

import com.android.moviewdemo.data.models.movies_list.MoviesListMain
import retrofit2.http.GET

open interface ApiService {

    @GET("movies_list")
    suspend fun getMoviesList(): MoviesListMain
}

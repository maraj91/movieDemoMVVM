package com.android.moviewdemo.repository

import com.android.moviewdemo.data.models.movies_list.MoviesListMain
import com.android.moviewdemo.data.remote.ResourceAPI
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {
    suspend fun getMoviesList(): Flow<ResourceAPI<MoviesListMain>>
}
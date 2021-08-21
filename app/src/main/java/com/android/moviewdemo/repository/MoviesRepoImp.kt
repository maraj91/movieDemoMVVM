package com.android.moviewdemo.repository

import com.android.moviewdemo.data.models.movies_list.MoviesListMain
import com.android.moviewdemo.data.remote.ApiService
import com.android.moviewdemo.data.remote.ResourceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is the single source of data for whole Movies app.
 */

@Singleton
class MoviesRepoImp @Inject constructor(private val apiService: ApiService) : MoviesRepo {

    override suspend fun getMoviesList(): Flow<ResourceAPI<MoviesListMain>> {
        return flow {
            emit(ResourceAPI.loading(data = null))
            try {
                emit(ResourceAPI.success(data = apiService.getMoviesList()))
            } catch (exception: Exception) {
                emit(ResourceAPI.error(data = null,error = exception, message = exception.message ?: ""))
            }
        }.flowOn(Dispatchers.IO)
    }
}
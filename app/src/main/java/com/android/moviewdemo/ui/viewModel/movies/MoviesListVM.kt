package com.android.moviewdemo.ui.viewModel.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.moviewdemo.data.models.movies_list.MoviesListMain
import com.android.moviewdemo.data.remote.ResourceAPI
import com.android.moviewdemo.repository.MoviesRepoImp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class MoviesListVM
@Inject constructor(private val moviesRepo: MoviesRepoImp): ViewModel() {

    var movieList = MutableLiveData<ResourceAPI<MoviesListMain>>()

    fun getMoviesList(){
        viewModelScope.launch {
            moviesRepo.getMoviesList().collect { response: ResourceAPI<MoviesListMain> ->
               movieList.value = response
            }
        }
    }
}
package com.android.moviewdemo.ui.fragment.moviesList

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.moviewdemo.R
import com.android.moviewdemo.base.BaseFragment
import com.android.moviewdemo.data.models.movies_list.MoviesData
import com.android.moviewdemo.data.models.movies_list.MoviesListMain
import com.android.moviewdemo.data.remote.ResourceAPI
import com.android.moviewdemo.databinding.FragmentMoviesListBinding
import com.android.moviewdemo.ui.activity.MainActivity
import com.android.moviewdemo.ui.adapter.moviesList.MoviesCallback
import com.android.moviewdemo.ui.adapter.moviesList.MoviesListAdapter
import com.android.moviewdemo.ui.viewModel.movies.MoviesListVM
import com.trapeze.maas_android.data.remote.Status

class FragmentMoviesList : BaseFragment<FragmentMoviesListBinding, MoviesListVM>(), MoviesCallback {

    var hasMoviesData : Boolean = false
    var movies: List<MoviesData> = arrayListOf()

    override fun subscribeToEvents(vm: MoviesListVM) {
        if(hasMoviesData)
            setUpRecyclerView(movies)
        else
            vm.getMoviesList()
        vm.movieList.observe(this@FragmentMoviesList, Observer { resourcesAPI ->
            resourcesAPI?.let { resourceAPI: ResourceAPI<MoviesListMain> ->
                when (resourceAPI.status) {
                    Status.LOADING -> showProgress()
                    Status.SUCCESS -> {
                        resourceAPI.data?.let {
                                data -> setUpRecyclerView(data.movies)
                                movies = data.movies
                                hasMoviesData = true
                        }
                        dismissProgress()
                    }
                    Status.ERROR -> dismissProgress()
                }
            }
        })
    }

    override val fragmentBinding: FragmentBinding
        get() = FragmentBinding(R.layout.fragment_movies_list, MoviesListVM::class.java)

    override fun onCreateFragment(savedInstanceState: Bundle?) {

    }

    override fun setAccessibilityView(binding: FragmentMoviesListBinding) {

    }

    private fun setUpRecyclerView(movies: List<MoviesData>) {
        val layoutManager = LinearLayoutManager(mContext)
        binding.recyclerViewMovies.layoutManager = layoutManager
        val moviesListAdapter = MoviesListAdapter()
        moviesListAdapter.setData(movies as ArrayList<MoviesData>)
        moviesListAdapter.setListener(this@FragmentMoviesList)
        binding.recyclerViewMovies.adapter = moviesListAdapter
    }

    override fun onMoviesClick(movie: MoviesData) {
        val bundle = bundleOf("movie" to movie)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_movies_to_movies_details, bundle)
    }
}
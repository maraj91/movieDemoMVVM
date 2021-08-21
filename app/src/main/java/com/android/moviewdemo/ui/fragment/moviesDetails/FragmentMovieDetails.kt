package com.android.moviewdemo.ui.fragment.moviesDetails

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.android.moviewdemo.R
import com.android.moviewdemo.base.BaseFragment
import com.android.moviewdemo.data.models.movies_list.MoviesData
import com.android.moviewdemo.databinding.FragmentMovieDetailsBinding
import com.android.moviewdemo.ui.viewModel.movies.MoviesListVM
import com.android.moviewdemo.utils.gone
import com.google.android.material.chip.Chip

class FragmentMovieDetails : BaseFragment<FragmentMovieDetailsBinding, MoviesListVM>() {

    override val fragmentBinding: FragmentBinding
        get() = FragmentBinding(R.layout.fragment_movie_details, MoviesListVM::class.java)

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        val movie = arguments?.getParcelable<MoviesData>("movie")
        if (movie == null)
            findNavController().popBackStack()
        else
            setUpData(movie)
    }

    private fun setUpData(movie: MoviesData) {
        binding.data = movie
        setupGenresChips(movie.genres)
        setupActorsChips(movie.cast)
    }

    private fun setupGenresChips(genres: List<String>?) {
        binding.apply {
            if (genres == null) {
                chipGroupGenres.gone()
                lblGenres.gone()
            }
            else {
                genres.forEach { genres ->
                    val chip = Chip(context)
                    chip.text = genres
                    chipGroupGenres.addView(chip)
                }
            }
        }
    }

    private fun setupActorsChips(actors: List<String>?) {
        binding.apply {
            if (actors == null) {
                lblActors.gone()
                chipGroupActors.gone()
            } else {
                actors.forEach { actor ->
                    val chip = Chip(context)
                    chip.text = actor
                    chipGroupActors.addView(chip)
                }
            }
        }
    }

    override fun setAccessibilityView(binding: FragmentMovieDetailsBinding) {

    }

    override fun subscribeToEvents(vm: MoviesListVM) {

    }
}
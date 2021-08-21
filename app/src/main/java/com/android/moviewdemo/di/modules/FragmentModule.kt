package com.android.moviewdemo.di.modules

import com.android.moviewdemo.ui.fragment.moviesDetails.FragmentMovieDetails
import com.android.moviewdemo.ui.fragment.moviesList.FragmentMoviesList
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindFragmentMoviesList(): FragmentMoviesList

    @ContributesAndroidInjector
    abstract fun bindFragmentMovieDetails(): FragmentMovieDetails

}
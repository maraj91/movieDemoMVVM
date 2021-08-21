package com.android.moviewdemo.di.modules

import com.android.moviewdemo.ui.activity.MainActivity
import com.android.moviewdemo.ui.fragment.moviesList.FragmentMoviesList
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
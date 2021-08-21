package com.android.moviewdemo.di.modules

import androidx.lifecycle.ViewModelProvider
import com.android.moviewdemo.di.factories.MoviesViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * This modules is responsible for creating ViewModels for screens with the help of MoviesViewModelProviderFactory Factory.
 */
@Module
interface ViewModelsFactoryModule {

    @Binds
    fun bindMoviesViewModelFactory(factory: MoviesViewModelProviderFactory) : ViewModelProvider.Factory
}
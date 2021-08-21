package com.android.moviewdemo.di.modules

import androidx.lifecycle.ViewModel
import com.android.moviewdemo.ui.viewModel.main.MainVM
import com.android.moviewdemo.ui.viewModel.movies.MoviesListVM
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesListVM::class)
    abstract fun bindMoviesListVm(viewModel: MoviesListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainVM::class)
    abstract fun bindMainVm(viewModel: MainVM): ViewModel
}
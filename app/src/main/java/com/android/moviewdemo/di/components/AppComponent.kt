package com.android.moviewdemo.di.components

import android.app.Application
import com.android.moviewdemo.app.MovieApplication
import com.android.moviewdemo.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        PreferencesModule::class,
        NetModule::class,
        ViewModelModule::class,
        ViewModelsFactoryModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<MovieApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MovieApplication)
}
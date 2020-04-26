package com.adrianlazaro.moviesapp.di

import android.app.Application
import com.adrianlazaro.moviesapp.ui.detail.MovieDetailFragmentComponent
import com.adrianlazaro.moviesapp.ui.detail.MovieDetailFragmentModule
import com.adrianlazaro.moviesapp.ui.main.MoviesFragmentComponent
import com.adrianlazaro.moviesapp.ui.main.MoviesFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MoviesComponent {

    fun plus(module: MoviesFragmentModule): MoviesFragmentComponent
    fun plus(module: MovieDetailFragmentModule): MovieDetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MoviesComponent
    }
}
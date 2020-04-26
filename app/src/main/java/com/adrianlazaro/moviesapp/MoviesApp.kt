package com.adrianlazaro.moviesapp

import android.app.Application
import com.adrianlazaro.moviesapp.di.DaggerMoviesComponent
import com.adrianlazaro.moviesapp.di.MoviesComponent

class MoviesApp : Application() {

    lateinit var component: MoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMoviesComponent
            .factory()
            .create(this)
    }
}
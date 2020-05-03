package com.adrianlazaro.moviesapp

import android.app.Application
import com.adrianlazaro.moviesapp.di.initDI

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
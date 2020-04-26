package com.adrianlazaro.moviesapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MoviesComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MoviesComponent
    }
}
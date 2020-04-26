package com.adrianlazaro.moviesapp.ui.detail

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MovieDetailFragmentModule(private val movieId: Int) {

    @Provides
    fun movieDetailViewModelProvider() : MovieDetailViewModel {
        return MovieDetailViewModel(movieId)
    }
}

@Subcomponent(modules = [(MovieDetailFragmentModule::class)])
interface MovieDetailFragmentComponent {
    val movieDetailViewModel: MovieDetailViewModel
}
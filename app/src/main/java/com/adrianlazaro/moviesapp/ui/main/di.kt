package com.adrianlazaro.moviesapp.ui.main

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MoviesFragmentModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MoviesViewModel(getPopularMovies)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent(modules = [(MoviesFragmentModule::class)])
interface MoviesFragmentComponent {
    val moviesViewModel: MoviesViewModel
}
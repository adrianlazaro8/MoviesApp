package com.adrianlazaro.moviesapp.ui.detail

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.usecases.GetMovieById
import com.adrianlazaro.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MovieDetailFragmentModule(private val movieId: Int) {

    @Provides
    fun movieDetailViewModelProvider(
        getMovieById: GetMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ) : MovieDetailViewModel {
        return MovieDetailViewModel(movieId, getMovieById, toggleMovieFavorite)
    }

    @Provides
    fun getMovieByIdUseCaseProvider(moviesRepository: MoviesRepository) = GetMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) = ToggleMovieFavorite(moviesRepository)
}

@Subcomponent(modules = [(MovieDetailFragmentModule::class)])
interface MovieDetailFragmentComponent {
    val movieDetailViewModel: MovieDetailViewModel
}
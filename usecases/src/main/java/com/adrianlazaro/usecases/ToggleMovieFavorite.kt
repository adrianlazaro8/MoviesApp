package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}
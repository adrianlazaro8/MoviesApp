package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}
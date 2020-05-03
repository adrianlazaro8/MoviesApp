package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.domain.Movie

class GetMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}
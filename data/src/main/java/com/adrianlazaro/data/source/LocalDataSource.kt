package com.adrianlazaro.data.source

import com.adrianlazaro.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getPopularMovies(): List<Movie>
    suspend fun findById(movieId: Int): Movie
    suspend fun update(movie: Movie)
}
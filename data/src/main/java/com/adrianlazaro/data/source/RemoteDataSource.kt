package com.adrianlazaro.data.source

import com.adrianlazaro.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}
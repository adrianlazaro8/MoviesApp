package com.adrianlazaro.moviesapp.data.network

import com.adrianlazaro.data.source.RemoteDataSource
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.common.toDomainMovie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .getPopularMovies(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}
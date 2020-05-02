package com.adrianlazaro.moviesapp.data.network

import com.adrianlazaro.data.source.RemoteDataSource
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.common.toDomainMovie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .getPopularMovies(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}
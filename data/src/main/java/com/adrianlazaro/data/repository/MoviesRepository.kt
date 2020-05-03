package com.adrianlazaro.data.repository

import com.adrianlazaro.data.source.LocalDataSource
import com.adrianlazaro.data.source.RemoteDataSource
import com.adrianlazaro.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {

    suspend fun getPopularMovies(): List<Movie> {

        if (localDataSource.isEmpty()) {
            val movies =
                remoteDataSource.getPopularMovies(apiKey, regionRepository.getLastRegion())
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun update(movie: Movie) = localDataSource.update(movie)

    suspend fun findById(movieId: Int): Movie = localDataSource.findById(movieId)
}
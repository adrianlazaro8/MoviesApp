package com.adrianlazaro.moviesapp.data.database

import com.adrianlazaro.data.source.LocalDataSource
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.common.toDomainMovie
import com.adrianlazaro.moviesapp.common.toRoomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(database: MovieDatabase) : LocalDataSource {

    private val movieDao = database.movieDao()

    override suspend fun isEmpty() = withContext(Dispatchers.IO) {
        movieDao.movieCount() <= 0
    }

    override suspend fun getPopularMovies() = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun findById(movieId: Int) = withContext(Dispatchers.IO) {
        movieDao.findById(movieId).toDomainMovie()
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {
        movieDao.saveMovies(movies.map { it.toRoomMovie() })
    }

    override suspend fun update(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.updateMovie(movie.toRoomMovie())
    }
}
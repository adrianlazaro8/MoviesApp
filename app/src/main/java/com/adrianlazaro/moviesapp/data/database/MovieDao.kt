package com.adrianlazaro.moviesapp.data.database

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieRoom>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): MovieRoom

    @Query("SELECT COUNT(id) FROM Movie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMovies(movies: List<MovieRoom>)

    @Update
    fun updateMovie(movie: MovieRoom)
}
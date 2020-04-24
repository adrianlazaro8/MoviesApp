package com.adrianlazaro.moviesapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDbDto
}
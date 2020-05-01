package com.adrianlazaro.moviesapp.fakes

import com.adrianlazaro.data.source.RemoteDataSource
import com.adrianlazaro.testshared.defaultFakeMovies

class FakeRemoteDataSource : RemoteDataSource {
    var movies = defaultFakeMovies

    override suspend fun getPopularMovies(apiKey: String, region: String) = movies
}
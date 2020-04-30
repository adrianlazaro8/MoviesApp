package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.testshared.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesTest {

    private lateinit var getPopularMovies: GetPopularMovies

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setup(){
        getPopularMovies = GetPopularMovies(moviesRepository)
    }

    @Test
    fun `Get popular movies should return list of movies`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1), mockedMovie.copy(id = 2))
            whenever(moviesRepository.getPopularMovies()).thenReturn(movies)

            val result = getPopularMovies.invoke()

            Assert.assertEquals(movies, result)
        }
    }

}
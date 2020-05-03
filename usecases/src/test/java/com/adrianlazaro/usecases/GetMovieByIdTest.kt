package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.testshared.mockedMovie
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieByIdTest {

    private lateinit var getMovieById: GetMovieById

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setup(){
        getMovieById = GetMovieById(moviesRepository)
    }

    @Test
    fun `movies repository request should return movie`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(moviesRepository.findById(1)).thenReturn(movie)

            val result = getMovieById.invoke(1)

            assertEquals(movie, result)
        }
    }
}
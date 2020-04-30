package com.adrianlazaro.usecases

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.testshared.mockedMovie
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ToggleMovieFavoriteTest {

    private lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        toggleMovieFavorite = ToggleMovieFavorite(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            val result = toggleMovieFavorite.invoke(movie)

            verify(moviesRepository).update(result)
        }
    }

    @Test
    fun `click favorite movie becomes unfavorite`() {
        runBlocking {
            val movie = mockedMovie.copy(favorite = true)

            val result = toggleMovieFavorite.invoke(movie)

            Assert.assertFalse(result.favorite)
        }
    }

    @Test
    fun `click unfavorite movie becomes favorite`() {
        runBlocking {
            val movie = mockedMovie.copy(favorite = false)

            val result = toggleMovieFavorite.invoke(movie)

            Assert.assertTrue(result.favorite)
        }
    }

}
package com.adrianlazaro.moviesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adrianlazaro.testshared.mockedMovie
import com.adrianlazaro.usecases.GetMovieById
import com.adrianlazaro.usecases.ToggleMovieFavorite
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMovieById: GetMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var observer: Observer<MovieDetailViewModel.UiModel>

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(1, getMovieById, toggleMovieFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the movie`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(getMovieById.invoke(1)).thenReturn(movie)

            viewModel.movie.observeForever(observer)

            verify(observer).onChanged(MovieDetailViewModel.UiModel(movie))
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(getMovieById.invoke(1)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))
            viewModel.movie.observeForever(observer)

            viewModel.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }
    }

}
package com.adrianlazaro.moviesapp.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel
import com.adrianlazaro.testshared.mockedMovie
import com.adrianlazaro.usecases.GetPopularMovies
import com.nhaarman.mockitokotlin2.any
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
class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observer: Observer<MoviesViewModel.UiState>

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            viewModel.uiState.observeForever(observer)

            viewModel.refresh()

            verify(observer).onChanged(MoviesViewModel.UiState.Loading)
        }
    }
}
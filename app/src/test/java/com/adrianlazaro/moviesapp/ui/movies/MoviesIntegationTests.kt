package com.adrianlazaro.moviesapp.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.data.repository.RegionRepository
import com.adrianlazaro.moviesapp.fakes.FakeLocalDataSource
import com.adrianlazaro.moviesapp.fakes.FakeLocationDataSource
import com.adrianlazaro.moviesapp.fakes.FakePermissionchecker
import com.adrianlazaro.moviesapp.fakes.FakeRemoteDataSource
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel
import com.adrianlazaro.testshared.defaultFakeMovies
import com.adrianlazaro.usecases.GetPopularMovies
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesIntegationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MoviesViewModel.UiState>

    private lateinit var getPopularMovies: GetPopularMovies

    private lateinit var viewModel: MoviesViewModel

    private var localDataSource = FakeLocalDataSource()

    private var remoteDataSource = FakeRemoteDataSource()

    @Before
    fun setup() {
        val moviesRepository = MoviesRepository(
            localDataSource,
            remoteDataSource,
            RegionRepository(FakePermissionchecker(), FakeLocationDataSource()),
            "123456"
        )

        getPopularMovies = GetPopularMovies(moviesRepository)

        viewModel = MoviesViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        remoteDataSource.movies = defaultFakeMovies
        localDataSource.movies = emptyList()
        viewModel.uiState.observeForever(observer)

        viewModel.requestMovies()

        verify(observer, times(2)).onChanged(MoviesViewModel.UiState.Content(defaultFakeMovies))
    }

    @Test
    fun `data is loaded from local source when available`() {
        remoteDataSource.movies = emptyList()
        localDataSource.movies = defaultFakeMovies
        viewModel.uiState.observeForever(observer)

        viewModel.requestMovies()

        verify(observer, times(2)).onChanged(MoviesViewModel.UiState.Content(defaultFakeMovies))
    }

}
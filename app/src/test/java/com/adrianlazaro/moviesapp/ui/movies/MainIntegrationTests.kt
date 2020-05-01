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
class MainIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MoviesViewModel.UiState>

    private lateinit var getPopularMovies: GetPopularMovies

    private lateinit var viewModel: MoviesViewModel

    private var fakeLocalDataSource = FakeLocalDataSource()

    private var fakeRemoteDataSource = FakeRemoteDataSource()

    @Before
    fun setup() {
        val moviesRepository = MoviesRepository(
            fakeLocalDataSource,
            fakeRemoteDataSource,
            RegionRepository(FakePermissionchecker(), FakeLocationDataSource()),
            "123456"
        )

        getPopularMovies = GetPopularMovies(moviesRepository)

        viewModel = MoviesViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        fakeRemoteDataSource.movies = defaultFakeMovies
        fakeLocalDataSource.movies = emptyList()
        viewModel.uiState.observeForever(observer)

        viewModel.refresh()

        verify(observer, times(2)).onChanged(MoviesViewModel.UiState.Content(defaultFakeMovies))
    }

    @Test
    fun `data is loaded from local source when available`() {
        fakeRemoteDataSource.movies = emptyList()
        fakeLocalDataSource.movies = defaultFakeMovies
        viewModel.uiState.observeForever(observer)

        viewModel.refresh()

        verify(observer, times(2)).onChanged(MoviesViewModel.UiState.Content(defaultFakeMovies))
    }

}
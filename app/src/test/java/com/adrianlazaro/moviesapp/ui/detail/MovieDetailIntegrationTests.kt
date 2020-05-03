package com.adrianlazaro.moviesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.data.repository.RegionRepository
import com.adrianlazaro.moviesapp.fakes.FakeLocalDataSource
import com.adrianlazaro.moviesapp.fakes.FakeLocationDataSource
import com.adrianlazaro.moviesapp.fakes.FakePermissionchecker
import com.adrianlazaro.moviesapp.fakes.FakeRemoteDataSource
import com.adrianlazaro.testshared.defaultFakeMovies
import com.adrianlazaro.testshared.mockedMovie
import com.adrianlazaro.usecases.GetMovieById
import com.adrianlazaro.usecases.ToggleMovieFavorite
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MovieDetailViewModel.UiModel>

    private lateinit var viewModel: MovieDetailViewModel

    private var localDataSource = FakeLocalDataSource()

    private var fakeRemoteDataSource = FakeRemoteDataSource()

    @Before
    fun setUp() {
        val moviesRepository = MoviesRepository(
            localDataSource,
            fakeRemoteDataSource,
            RegionRepository(FakePermissionchecker(), FakeLocationDataSource()),
            "123456"
        )

        val getMovieById = GetMovieById(moviesRepository)
        val toggleMovieFavorite = ToggleMovieFavorite(moviesRepository)
        viewModel = MovieDetailViewModel(1, getMovieById, toggleMovieFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the movie`() {
        localDataSource.movies = defaultFakeMovies
        viewModel.movie.observeForever(observer)

        verify(observer).onChanged(MovieDetailViewModel.UiModel(mockedMovie.copy(1)))
    }

    @Test
    fun `favorite is updated in local data source`() {
        localDataSource.movies = defaultFakeMovies
        viewModel.movie.observeForever(observer)

        viewModel.onFavoriteClicked()

        runBlocking {
            Assert.assertTrue(localDataSource.findById(1).favorite)
        }
    }
}
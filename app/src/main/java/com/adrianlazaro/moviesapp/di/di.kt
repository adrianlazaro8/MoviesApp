package com.adrianlazaro.moviesapp.di

import android.app.Application
import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.data.repository.RegionRepository
import com.adrianlazaro.data.source.LocalDataSource
import com.adrianlazaro.data.source.LocationDataSource
import com.adrianlazaro.data.source.PermissionCheckerDataSource
import com.adrianlazaro.data.source.RemoteDataSource
import com.adrianlazaro.moviesapp.BuildConfig
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.data.AndroidPermissionChecker
import com.adrianlazaro.moviesapp.data.PlayServicesLocationDataSource
import com.adrianlazaro.moviesapp.data.database.MovieDatabase
import com.adrianlazaro.moviesapp.data.database.RoomDataSource
import com.adrianlazaro.moviesapp.data.network.TheMovieDb
import com.adrianlazaro.moviesapp.data.network.TheMovieDbDataSource
import com.adrianlazaro.moviesapp.ui.detail.MovieDetailFragment
import com.adrianlazaro.moviesapp.ui.detail.MovieDetailViewModel
import com.adrianlazaro.moviesapp.ui.main.MoviesFragment
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel
import com.adrianlazaro.usecases.GetMovieById
import com.adrianlazaro.usecases.GetPopularMovies
import com.adrianlazaro.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { BuildConfig.MOVIE_DB_API_KEY }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }

    single { TheMovieDb(get(named("baseUrl"))) }
    single { MovieDatabase.build(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }

    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionCheckerDataSource> { AndroidPermissionChecker(get()) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MoviesFragment>()) {
        viewModel { MoviesViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<MovieDetailFragment>()) {
        viewModel { (id: Int) -> MovieDetailViewModel(id, get(), get(), get()) }
        scoped { GetMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}
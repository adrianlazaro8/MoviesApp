package com.adrianlazaro.moviesapp.di

import android.app.Application
import androidx.core.content.PermissionChecker
import androidx.room.Room
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
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = BuildConfig.MOVIE_DB_API_KEY

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun theMovieDbProvider(): TheMovieDb = TheMovieDb("https://api.themoviedb.org/3/")

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource(theMovieDbProvider())

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionCheckerDataSource =
        AndroidPermissionChecker(app)

}
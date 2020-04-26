package com.adrianlazaro.moviesapp.di

import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.data.repository.RegionRepository
import com.adrianlazaro.data.source.LocalDataSource
import com.adrianlazaro.data.source.LocationDataSource
import com.adrianlazaro.data.source.PermissionCheckerDataSource
import com.adrianlazaro.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionCheckerDataSource
    ) = RegionRepository(permissionChecker, locationDataSource)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)

}
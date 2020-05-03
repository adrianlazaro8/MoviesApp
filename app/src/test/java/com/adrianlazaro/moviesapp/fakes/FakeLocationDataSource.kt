package com.adrianlazaro.moviesapp.fakes

import com.adrianlazaro.data.source.LocationDataSource

class FakeLocationDataSource : LocationDataSource {

    var location = "US"

    override suspend fun getLastRegion(): String? = location
}
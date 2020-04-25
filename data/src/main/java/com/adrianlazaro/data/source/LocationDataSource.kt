package com.adrianlazaro.data.source

interface LocationDataSource {
    suspend fun getLastRegion(): String
}
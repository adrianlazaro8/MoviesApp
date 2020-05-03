package com.adrianlazaro.data.repository

import com.adrianlazaro.data.source.LocationDataSource
import com.adrianlazaro.data.source.PermissionCheckerDataSource

class RegionRepository(
    private val permissionCheckerDataSource: PermissionCheckerDataSource,
    private val locationDataSource: LocationDataSource
) {
    companion object {
        const val DEFAULT_REGION = "ES"
    }

    suspend fun getLastRegion(): String {
        return if (permissionCheckerDataSource.check(PermissionCheckerDataSource.Permission.COARSE_LOCATION)) {
            locationDataSource.getLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
package com.adrianlazaro.moviesapp.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.adrianlazaro.data.source.PermissionCheckerDataSource

class AndroidPermissionChecker(private val application: Application) : PermissionCheckerDataSource {

    override suspend fun check(permission: PermissionCheckerDataSource.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
}

private fun PermissionCheckerDataSource.Permission.toAndroidId() = when (this) {
    PermissionCheckerDataSource.Permission.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
}
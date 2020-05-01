package com.adrianlazaro.moviesapp.fakes

import com.adrianlazaro.data.source.PermissionCheckerDataSource

class FakePermissionchecker : PermissionCheckerDataSource {

    var permissionGranted = true

    override suspend fun check(permission: PermissionCheckerDataSource.Permission): Boolean = permissionGranted

}
package com.adrianlazaro.data.source

interface PermissionCheckerDataSource {
    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}
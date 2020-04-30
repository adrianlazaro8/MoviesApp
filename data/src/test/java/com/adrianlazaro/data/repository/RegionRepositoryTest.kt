package com.adrianlazaro.data.repository

import com.adrianlazaro.data.source.LocationDataSource
import com.adrianlazaro.data.source.PermissionCheckerDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    private lateinit var regionRepository: RegionRepository

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionCheckerDataSource: PermissionCheckerDataSource

    @Before
    fun setUp() {
        regionRepository = RegionRepository(permissionCheckerDataSource, locationDataSource)
    }

    @Test
    fun `returns default location when coarse permission is not granted`() {
        runBlocking {
            whenever(permissionCheckerDataSource.check(PermissionCheckerDataSource.Permission.COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.getLastRegion()

            assertEquals(RegionRepository.DEFAULT_REGION, region)
        }
    }

    @Test
    fun `returns region from location data source when permission granted`() {
        runBlocking {
            whenever(permissionCheckerDataSource.check(PermissionCheckerDataSource.Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.getLastRegion()).thenReturn("ES")

            val region = regionRepository.getLastRegion()

            assertEquals("ES", region)
        }
    }
}
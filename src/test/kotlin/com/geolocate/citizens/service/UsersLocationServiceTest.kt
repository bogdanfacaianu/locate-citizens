package com.geolocate.citizens.service

import com.geolocate.citizens.LONDON_CITY
import com.geolocate.citizens.COUNTRY_CODE
import com.geolocate.citizens.DISTANCE
import com.geolocate.citizens.LONDON_COORDINATES
import com.geolocate.citizens.LONDON_LATITUDE
import com.geolocate.citizens.LONDON_LONGITUDE
import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.location.GeoLocation
import com.geolocate.citizens.entity.location.GeoLocationCache
import com.geolocate.citizens.getUsersForLocation
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test

class UsersLocationServiceTest {

    private val geoLocationCache: GeoLocationCache = mockk()
    private val userService: UserService = mockk()

    private val usersLocationService = UsersLocationService(geoLocationCache, userService)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `getting user data in location not found returns an empty result`() {
        given_LocationCacheCannotFindLocationCoordinates()

        val result = usersLocationService.getUserDataInLocation(LONDON_CITY, DISTANCE, COUNTRY_CODE)

        result shouldBe emptySet()
    }

    @Test
    fun `getting user data in location returns found results`() {
        given_LocationCacheReturnsLondonCoordinates()
        given_theUsersFoundInLocationRadius()

        val result = usersLocationService.getUserDataInLocation(LONDON_CITY, DISTANCE, COUNTRY_CODE)

        result shouldBe getUsersForLocation()
    }

    private fun given_LocationCacheCannotFindLocationCoordinates() {
        every { geoLocationCache.getGeoLocation(LONDON_CITY, COUNTRY_CODE) } returns null
    }

    private fun given_LocationCacheReturnsLondonCoordinates() {
        val geoLocation = GeoLocation("locationKey", LONDON_CITY, COUNTRY_CODE, Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE))
        every { geoLocationCache.getGeoLocation(LONDON_CITY, COUNTRY_CODE) } returns geoLocation
    }

    private fun given_theUsersFoundInLocationRadius() {
        every { userService.getUsersForData(LONDON_CITY, DISTANCE, LONDON_COORDINATES) } returns getUsersForLocation()
    }
}
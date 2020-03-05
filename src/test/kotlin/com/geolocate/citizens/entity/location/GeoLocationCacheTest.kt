package com.geolocate.citizens.entity.location

import com.geolocate.citizens.COUNTRY_CODE
import com.geolocate.citizens.LATITUDE
import com.geolocate.citizens.LONDON_CITY
import com.geolocate.citizens.LONDON_LATITUDE
import com.geolocate.citizens.LONDON_LONGITUDE
import com.geolocate.citizens.LONGITUDE
import com.geolocate.citizens.entity.coordinates.Coordinates
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GeoLocationCacheTest {

    private val geoLocationFinder: GeoLocationFinder = mockk(relaxed = true)
    private val geoLocationCache = GeoLocationCache(geoLocationFinder)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        geoLocationCache.clear()
    }

    @Test
    fun `when no location exists attempt to look for coordinates`() {
        val geoLocation = given_cacheHasLocation(LONDON_CITY, LONDON_LATITUDE, LONDON_LONGITUDE)

        val result = geoLocationCache.getGeoLocation(LONDON_CITY, COUNTRY_CODE)

        result shouldBe geoLocation
        verify(exactly = 1) { geoLocationFinder.getCoordinates(LONDON_CITY) }
    }

    @Test
    fun `when cache has location record then return result`() {
        val geoLocation = given_cacheHasLocation(LONDON_CITY, LATITUDE, LONGITUDE)

        val result = geoLocationCache.getGeoLocation(LONDON_CITY, COUNTRY_CODE)

        result shouldBe geoLocation
        verify(exactly = 1) { geoLocationFinder.getCoordinates(LONDON_CITY) }
    }

    @Test
    fun `when cache has locations, clearing it should have an empty cache`() {
        given_cacheHasLocation("Bristol", LATITUDE, LONGITUDE)
        val geoLocation = given_cacheHasLocation(LONDON_CITY, LONDON_LATITUDE, LONDON_LONGITUDE)

        val result = geoLocationCache.getGeoLocation(LONDON_CITY, COUNTRY_CODE)

        result shouldBe geoLocation
        verify(exactly = 1) { geoLocationFinder.getCoordinates(LONDON_CITY) }
    }

    private fun given_cacheHasLocation(location: String, lat: Double, lng: Double): GeoLocation {
        val geoLocation = given_locationLookup(location, lat, lng)

        geoLocationCache.getGeoLocation(location, COUNTRY_CODE)

        return geoLocation
    }

    private fun given_locationLookup(location: String, lat: Double, lng: Double): GeoLocation {
        val coordinates = Coordinates(lat, lng)
        every { geoLocationFinder.getCoordinates(LONDON_CITY) } returns coordinates

        return GeoLocation(buildCacheKey(location), location, COUNTRY_CODE, coordinates)
    }

    private fun buildCacheKey(location: String) = String.format("%s_%s", location, COUNTRY_CODE)
}
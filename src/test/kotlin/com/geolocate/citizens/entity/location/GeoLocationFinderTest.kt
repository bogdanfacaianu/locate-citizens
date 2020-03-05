package com.geolocate.citizens.entity.location

import com.byteowls.jopencage.JOpenCageGeocoder
import com.geolocate.citizens.LONDON_CITY
import com.geolocate.citizens.LATITUDE
import com.geolocate.citizens.LONGITUDE
import com.geolocate.citizens.entity.coordinates.Coordinates
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClientException

class GeoLocationFinderTest {

    private val jOpenCageGeocoder: JOpenCageGeocoder = mockk(relaxed = true)

    private val geoLocationFinder = GeoLocationFinder(jOpenCageGeocoder)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `when request is made but service is unavailable then return no response`() {
        every { jOpenCageGeocoder.forward(any()) } throws RestClientException("i felt flaky today")

        val coordinates = geoLocationFinder.getCoordinates(LONDON_CITY)

        coordinates shouldBe null
    }

    @Test
    fun `when request is made return coordinates`() {
        val coordinates = geoLocationFinder.getCoordinates(LONDON_CITY)

        coordinates shouldBe Coordinates(LATITUDE, LONGITUDE)
    }
}
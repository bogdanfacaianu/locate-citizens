package com.geolocate.citizens.entity.location

import com.byteowls.jopencage.JOpenCageGeocoder
import com.byteowls.jopencage.model.JOpenCageForwardRequest
import com.byteowls.jopencage.model.JOpenCageLatLng
import com.byteowls.jopencage.model.JOpenCageResponse
import com.geolocate.citizens.CITY
import com.geolocate.citizens.LONDON_LATITUDE
import com.geolocate.citizens.LONDON_LONGITUDE
import com.geolocate.citizens.entity.coordinates.Coordinates
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class GeoLocationFinderTest {

    private val jOpenCageGeocoder: JOpenCageGeocoder = mockk()

    private val geoLocationFinder = GeoLocationFinder(jOpenCageGeocoder)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `when request is made but service is unavailable then return no response`() {
        given_requestReturnsNoResponse()

        val coordinates = geoLocationFinder.getCoordinates(CITY)

        coordinates shouldBe null
    }

    @Test
    fun `when request is made return coordinates`() {
        given_requestReturnsCoordinates()

        val coordinates = geoLocationFinder.getCoordinates(CITY, "usa")

        coordinates shouldBe Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)
    }

    private fun given_requestReturnsCoordinates() {
        val jOpenCageLatLng = JOpenCageLatLng()
        jOpenCageLatLng.lat = LONDON_LATITUDE
        jOpenCageLatLng.lng = LONDON_LONGITUDE
        val jOpenCageResponse = mock(JOpenCageResponse::class.java)
        val request = JOpenCageForwardRequest(CITY)
        every { jOpenCageGeocoder.forward(request) } returns jOpenCageResponse
        every { jOpenCageResponse.firstPosition } returns jOpenCageLatLng
    }

    private fun given_requestReturnsNoResponse() {
        val request = JOpenCageForwardRequest(CITY)
       every { jOpenCageGeocoder.forward(request) } returns null
    }
}
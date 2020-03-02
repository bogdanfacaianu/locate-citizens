package com.geolocate.citizens.entity.location

import com.byteowls.jopencage.JOpenCageGeocoder
import com.byteowls.jopencage.model.JOpenCageForwardRequest
import com.byteowls.jopencage.model.JOpenCageLatLng
import com.byteowls.jopencage.model.JOpenCageResponse
import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.location.GeoLocation.Companion.UK_COUNTRY_CODE
import mu.KotlinLogging
import org.springframework.stereotype.Component


private val logger = KotlinLogging.logger {}

@Component
class GeoLocationFinder(
        private val jOpenCageGeocoder: JOpenCageGeocoder
) {

    fun getCoordinates(location: String, countryCode: String? = UK_COUNTRY_CODE): Coordinates? {
        val firstLocationFound = buildRequest(location, countryCode)

        return buildResult(firstLocationFound, location)
    }

    private fun buildRequest(location: String, countryCode: String?): JOpenCageLatLng? {
        val request = JOpenCageForwardRequest(location)
        setOrDefaultCountryRestriction(countryCode, request)
        request.limit = 1;
        request.isNoAnnotations = true;

        val response: JOpenCageResponse = jOpenCageGeocoder.forward(request)
        return response.firstPosition
    }

    private fun buildResult(locationFound: JOpenCageLatLng?, location: String): Coordinates? {
        return locationFound?.let {
            Coordinates(locationFound.lat, locationFound.lng)
        } ?: run {
            logger.warn { "No coordinates could be retrieved for location $location" }
            null
        }
    }

    private fun setOrDefaultCountryRestriction(countryCode: String?, request: JOpenCageForwardRequest) {
        if (countryCode.isNullOrBlank()) {
            request.restrictToCountryCode = UK_COUNTRY_CODE
        } else {
            request.restrictToCountryCode = countryCode
        }
    }

}
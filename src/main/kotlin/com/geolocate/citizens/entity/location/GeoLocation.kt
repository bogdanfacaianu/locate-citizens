package com.geolocate.citizens.entity.location

import com.geolocate.citizens.entity.coordinates.Coordinates

data class GeoLocation(
        val key: String,
        val location: String,
        val countryCode: String? = UK_COUNTRY_CODE,
        val coordinates: Coordinates
) {

    companion object {
        const val UK_COUNTRY_CODE = "UK"
    }
}
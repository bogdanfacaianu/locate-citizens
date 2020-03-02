package com.geolocate.citizens.entity.coordinates

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class Coordinates(
        val latitude: Double,
        val longitude: Double
) {

    // Distance calculation with the Haversine Formula implemented from
    // https://www.movable-type.co.uk/scripts/latlong.html

    fun computeDistanceFrom(destination: Coordinates): Double {
        if (this.latitude == destination.latitude && this.longitude == destination.longitude)
            return 0.0

        val latitudeDifferenceInRadians = Math.toRadians(destination.latitude - this.latitude)
        val longitudeDifferenceInRadians = Math.toRadians(destination.longitude - this.longitude)
        val sourceLatitudeInRadians = Math.toRadians(this.latitude)
        val destinationLatitudeInRadians = Math.toRadians(destination.latitude)

        val a = sin(latitudeDifferenceInRadians / 2).pow(2) +
                (sin(longitudeDifferenceInRadians / 2).pow(2)) *
                cos(sourceLatitudeInRadians) * cos(destinationLatitudeInRadians)

        val c = 2 * asin(sqrt(a))

        return earthRadiusInMiles * c
    }

    companion object {
        private const val earthRadiusInMiles: Double = 3959.8743
    }
}
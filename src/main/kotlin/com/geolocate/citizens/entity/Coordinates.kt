package com.geolocate.citizens.entity

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class Coordinates(
        val latitude: Double,
        val longitude: Double
) {

    // Distance calculation formula implemented from
    // https://www.movable-type.co.uk/scripts/latlong.html

    fun computeDistanceFrom(destination: Coordinates): Double {
        if (this.latitude == destination.latitude && this.longitude == destination.longitude)
            return 0.0

        val latitudeDifference = Math.toRadians(destination.latitude - this.latitude)
        val longitudeDifference = Math.toRadians(destination.longitude - this.longitude)
        val originalLatitude = Math.toRadians(this.latitude)
        val originalLongitude = Math.toRadians(destination.latitude)

        val a = sin(latitudeDifference / 2).pow(2.toDouble()) +
                cos(originalLatitude) * cos(originalLongitude) *
                sin(longitudeDifference / 2).pow(2.toDouble())

        val c = 2 * asin(sqrt(a))

        return earthRadiusMiles * c
    }

    companion object {
        private const val earthRadiusMiles: Double = 3959.8743
    }
}
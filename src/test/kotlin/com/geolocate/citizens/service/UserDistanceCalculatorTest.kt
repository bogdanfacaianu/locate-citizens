package com.geolocate.citizens.service

import com.geolocate.citizens.CITY
import com.geolocate.citizens.DISTANCE
import com.geolocate.citizens.LONDON_LATITUDE
import com.geolocate.citizens.LONDON_LONGITUDE
import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.getAllUsersOutsideRadius
import com.geolocate.citizens.getUsersForLocation
import com.geolocate.citizens.verify_allLondonUsersHaveBeenRetrieved
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class UserDistanceCalculatorTest {

    @Test
    fun `distance is calculated between coordinates and new updated list is returned`() {
        val allLondonUsers = getUsersForLocation()

        val result = setUsersWithDistance(allLondonUsers, DISTANCE, centralPoint, CITY)

        verify_allLondonUsersHaveBeenRetrieved(result)
    }

    @Test
    fun `empty list is returned if all locations are too far`() {
        val allUsersOutsideRadius = getAllUsersOutsideRadius()

        val result = setUsersWithDistance(allUsersOutsideRadius, DISTANCE, centralPoint, CITY)

        result shouldBe emptySet()

    }

    @Test
    fun `empty list is returned if no users found`() {
        val result = setUsersWithDistance(emptySet(), DISTANCE, centralPoint, CITY)

        result shouldBe emptySet()
    }

    companion object {
        val centralPoint = Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)
    }

}
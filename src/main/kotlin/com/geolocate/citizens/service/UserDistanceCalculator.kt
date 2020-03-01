package com.geolocate.citizens.service

import com.geolocate.citizens.entity.Coordinates
import com.geolocate.citizens.entity.response.User
import org.springframework.stereotype.Service

@Service
class UserDistanceCalculator {

    fun checkUserDistance(user: User, limitLocation: Coordinates, distanceLimit: Double): Boolean {
        return updateUserWithDistance(user, limitLocation) <= distanceLimit
    }

    private fun updateUserWithDistance(user: User, limitLocation: Coordinates): Double {
        val userLocation = user.toCoordinates()
        val userDistance = userLocation.computeDistanceFrom(limitLocation)
        user.updateDistance(userDistance)
        return userDistance
    }

}
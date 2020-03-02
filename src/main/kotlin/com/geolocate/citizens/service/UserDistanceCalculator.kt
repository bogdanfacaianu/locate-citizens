package com.geolocate.citizens.service

import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.user.response.User
import org.springframework.stereotype.Service

@Service
class UserDistanceCalculator {

    fun setUsersWithDistance(
            users: Collection<User>,
            distance: Double,
            centralPoint: Coordinates,
            location: String
    ): Set<User> = users
            .filter { computeUserDistance(it, centralPoint) <= distance }
            .map {
                val userDistance = computeUserDistance(it, centralPoint)
                it.setDistanceAndLocation(userDistance, location)
            }.toSet()

    fun computeUserDistance(
            user: User,
            centralPoint: Coordinates
    ): Double = user.toCoordinates().computeDistanceFrom(centralPoint)

}
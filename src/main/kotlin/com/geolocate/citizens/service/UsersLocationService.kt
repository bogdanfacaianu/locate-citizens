package com.geolocate.citizens.service

import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.location.GeoLocationCache
import com.geolocate.citizens.entity.user.response.User
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UsersLocationService(
        private val geoLocationCache: GeoLocationCache,
        private val userService: UserService
) {

    fun getUserDataInLocation(location: String, distance: Double, countryCode: String): Collection<User> {
        val locationCoordinates = retrieveLocationCoordinates(location, countryCode)
        return locationCoordinates?.let {
            userService.getUsersForData(location, distance, locationCoordinates)
        } ?: run {
            logger.warn { "Could not retrieve coordinates for location $location, returning no user data" }
            emptySet<User>()
        }
    }

    private fun retrieveLocationCoordinates(location: String, countryCode: String): Coordinates? {
        return geoLocationCache.getGeoLocation(location, countryCode)?.coordinates
    }
}
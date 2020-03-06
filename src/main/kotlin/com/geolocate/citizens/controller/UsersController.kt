package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.location.GeoLocation.Companion.UK_COUNTRY_CODE
import com.geolocate.citizens.service.UsersLocationService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class UsersController(
        private val usersLocationService: UsersLocationService
) {

    @GetMapping("/getAllUsersInLocation")
    fun getAllUsersInLocation(@RequestParam(defaultValue = "London") location: String,
                              @RequestParam(defaultValue = UK_COUNTRY_CODE) countryCode: String,
                              @RequestParam(defaultValue = "50.0") distance: Double
    ): ResponseEntity<*> {
        val foundUsers = usersLocationService
                .getUserDataInLocation(location.capitalize(), validateDistance(distance), countryCode)
        return ResponseEntity.ok(foundUsers)
    }

    private fun validateDistance(distance: Double): Double {
        if (distance.isFinite() && distance < 0.0) {
            logger.info { "Given distance=$distance argument was invalid, using default: $DEFAULT_DISTANCE" }
            return DEFAULT_DISTANCE
        }
        return distance
    }

    companion object {
        private const val DEFAULT_DISTANCE = 50.0
    }

}
package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.location.GeoLocation.Companion.UK_COUNTRY_CODE
import com.geolocate.citizens.service.UsersLocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(
        private val usersLocationService: UsersLocationService
) {

   @GetMapping("/getAllUsersInLocation")
    fun getAllUsersInLocation(@RequestParam(defaultValue = "London") location: String,
                              @RequestParam(defaultValue = UK_COUNTRY_CODE) countryCode: String,
                              @RequestParam(defaultValue = "50.0") distance: Double
    ): ResponseEntity<*> {
        val foundUsers = usersLocationService.getUserDataInLocation(location.capitalize(), distance, countryCode)
        return ResponseEntity.ok(foundUsers)
    }

}
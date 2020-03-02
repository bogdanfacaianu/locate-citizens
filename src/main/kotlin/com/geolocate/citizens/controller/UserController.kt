package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.location.GeoLocation.Companion.UK_COUNTRY_CODE
import com.geolocate.citizens.service.UsersInLocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val usersInLocationService: UsersInLocationService
) {

   @GetMapping("/getAllUsersInLocation")
    fun getAllUsersInLocation(@RequestParam(defaultValue = "London") location: String,
                              @RequestParam(defaultValue = UK_COUNTRY_CODE) countryCode: String,
                              @RequestParam(defaultValue = "50.0") distance: Double
    ): ResponseEntity<*> {
        val foundUsers = usersInLocationService.getUserDataInLocation(location.capitalize(), distance, countryCode)
        if (foundUsers.isEmpty()) {
            return ResponseEntity<Error>(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(foundUsers)
    }

}
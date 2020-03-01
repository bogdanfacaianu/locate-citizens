package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.api.GeoLocationFinder.Companion.UK_COUNTRY_CODE
import com.geolocate.citizens.entity.api.UsersApiOperations
import com.geolocate.citizens.entity.response.User
import com.geolocate.citizens.service.UserService
import com.geolocate.citizens.service.UsersInLocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val usersApiOperations: UsersApiOperations,
        private val usersInLocationService: UsersInLocationService,
        private val userService: UserService

) {

    @GetMapping("/cityUsers/{city}")
    fun getAllCityUsers(@PathVariable city: String): Collection<User> {
        return userService.getAllUsersInLocation(city)
    }

    @GetMapping("/users")
    fun getAllUsers(): Collection<User> {
        return userService.findAllUsers()
    }

    @GetMapping("/user/{id}")
    fun getUsersInformationById(@PathVariable id: String): User? {
        return this.usersApiOperations.getUserDataById(id)
    }

    @GetMapping("/getAllUsersInLocation")
    fun getAllUsersInLocation(@RequestParam(defaultValue = "London") location: String,
                              @RequestParam(defaultValue = UK_COUNTRY_CODE) countryCode: String?,
                              @RequestParam(defaultValue = "50.0") distance: Double
    ): ResponseEntity<*> {
        val foundUsers = usersInLocationService.getUserDataInLocation(location, distance, countryCode)
        if (foundUsers.isEmpty()) {
            return ResponseEntity<Error>(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(foundUsers)
    }

}
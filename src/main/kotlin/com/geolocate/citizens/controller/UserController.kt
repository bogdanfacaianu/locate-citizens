package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.api.ApiRestOperations
import com.geolocate.citizens.entity.response.CityUser
import com.geolocate.citizens.entity.response.User
import com.geolocate.citizens.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val apiRestOperations: ApiRestOperations,
        private val userService: UserService
) {

    @GetMapping("/cityUsers/{city}")
    fun getAllCityUsers(@PathVariable city: String): List<User> {
        return apiRestOperations.getAllCityUsers(city)
        // return userService.getUsersDataForLocation(city)
    }

    @GetMapping("/users")
    fun getAllUsers(): List<CityUser> {
        return apiRestOperations.getAllUsers()
    }

    @GetMapping("/user/{id}")
    fun getUsersInformationById(@PathVariable id: String): User? {
        return apiRestOperations.getUserDataById(id)
    }

    @GetMapping("/instructions")
    fun getTaskInstructions(@PathVariable id: String): String? {
        return apiRestOperations.getInstructions()
    }
}
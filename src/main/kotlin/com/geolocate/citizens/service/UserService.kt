package com.geolocate.citizens.service

import com.geolocate.citizens.entity.Coordinates
import com.geolocate.citizens.entity.api.UsersApiOperations
import com.geolocate.citizens.entity.response.User
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService(
        private val usersApiOperations: UsersApiOperations,
        private val userDistanceCalculator: UserDistanceCalculator
) {

    fun getUsersForData(
            location: String,
            distance: Double,
            locationCoordinates: Coordinates
    ): Collection<User> = getUsersInSpecifiedRange(location, distance, locationCoordinates)

    private fun getAllUsersInLocation(
            location: String
    ): Collection<User> = findUsersFromLocation(location)

    private fun getUsersInSpecifiedRange(
            location: String,
            distance: Double,
            extremityCoordinates: Coordinates
    ): Collection<User> {
        val allUsers = filterUsersDistance(findAllUsers(), distance, extremityCoordinates, location)
        val usersInLocation = filterUsersDistance(getAllUsersInLocation(location), distance, extremityCoordinates, location)
        return allUsers.union(usersInLocation)
    }

    private fun filterUsersDistance(
            users: Collection<User>,
            distance: Double,
            extremityCoordinates: Coordinates,
            location: String
    ): Collection<User> = userDistanceCalculator.setUsersWithDistance(users, distance, extremityCoordinates, location)

    private fun findAllUsers() = usersApiOperations.getAllUsers()

    private fun findUsersFromLocation(location: String) = usersApiOperations.getAllCityUsers(location)
}
package com.geolocate.citizens.service

import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.user.UsersApiOperations
import com.geolocate.citizens.entity.user.response.User
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
            centralPoint: Coordinates
    ): Collection<User> = getUsersInSpecifiedRange(location, distance, centralPoint)

    private fun getAllUsersInLocation(
            location: String
    ): Collection<User> = findUsersFromLocation(location)

    private fun getUsersInSpecifiedRange(
            location: String,
            distance: Double,
            centralPoint: Coordinates
    ): Collection<User> {
        val allUsers = filterUsersDistance(findAllUsers(), distance, centralPoint, location)
        val usersInLocation = filterUsersDistance(getAllUsersInLocation(location), distance, centralPoint, location)
        return allUsers.union(usersInLocation)
    }

    private fun filterUsersDistance(
            users: Collection<User>,
            distance: Double,
            centralPoint: Coordinates,
            location: String
    ): Collection<User> = userDistanceCalculator.setUsersWithDistance(users, distance, centralPoint, location)

    private fun findAllUsers() = usersApiOperations.getAllUsers()

    private fun findUsersFromLocation(location: String) = usersApiOperations.getAllCityUsers(location)
}
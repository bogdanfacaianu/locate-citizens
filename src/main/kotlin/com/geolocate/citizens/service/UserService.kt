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

    fun getUsersForData(location: String?, distance: Double, locationCoordinates: Coordinates): Collection<User> {
        return getUsersInSpecifiedRange(location, distance, locationCoordinates)
    }

    fun getAllUsersInLocation(location: String?): Collection<User> {
        return location?.let {
            findUsersFromLocation(location)
        } ?: run {
            logger.debug { "No location provided for fetching users" }
            return emptySet()
        }
    }

    private fun getUsersInSpecifiedRange(location: String?, distance: Double, extremityCoordinates: Coordinates): Collection<User> {
        val allUsers = filterUsersDistance(findAllUsers(), distance, extremityCoordinates)
        val usersInLocation = filterUsersDistance(getAllUsersInLocation(location), distance, extremityCoordinates)
        return allUsers.union(usersInLocation)
    }

    private fun filterUsersDistance(users: Collection<User>, distance: Double, extremityCoordinates: Coordinates): Collection<User> {
        return users.filter { user -> evaluateUserDistance(user, distance, extremityCoordinates) }.toSet()
    }

    private fun evaluateUserDistance(
            user: User,
            distanceLimit: Double,
            extremityCoordinates: Coordinates
    ) = userDistanceCalculator.checkUserDistance(user, extremityCoordinates, distanceLimit)

    fun findAllUsers() = usersApiOperations.getAllUsers()

    private fun findUsersFromLocation(location: String) = usersApiOperations.getAllCityUsers(location)
}
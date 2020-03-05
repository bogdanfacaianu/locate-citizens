package com.geolocate.citizens.service

import com.geolocate.citizens.LONDON_CITY
import com.geolocate.citizens.DISTANCE
import com.geolocate.citizens.LONDON_COORDINATES
import com.geolocate.citizens.entity.user.UsersApiOperations
import com.geolocate.citizens.getAllLondonUsers
import com.geolocate.citizens.getAllUsers
import com.geolocate.citizens.getAllUsersOutsideRadius
import com.geolocate.citizens.getUsersForLocation
import com.geolocate.citizens.verify_allLondonUsersHaveBeenRetrieved
import com.geolocate.citizens.verify_allUsersHaveInitialDistanceOfZero
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test

class UserServiceTest {
    private val usersApiOperations: UsersApiOperations = mockk(relaxed = true)

    private val userService = UserService(usersApiOperations)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `given users from everywhere looking for all users should return filtered results`() {
        given_allTheUsersFoundInRadius()
        given_theUsersFoundInLocationAndRadius()

        val response = userService.getUsersForData(LONDON_CITY, DISTANCE, LONDON_COORDINATES)

        verify_allLondonUsersHaveBeenRetrieved(response, getAllLondonUsers())
    }

    @Test
    fun `given users only outside the radius looking for all users should return an empty result`() {
        given_allTheUsersFoundOutsideRadius()

        val response = userService.getUsersForData(LONDON_CITY, DISTANCE, LONDON_COORDINATES)

        response shouldBe emptySet()
    }

    private fun given_theUsersFoundInLocationAndRadius() {
        val allUsers = getAllUsers()
        verify_allUsersHaveInitialDistanceOfZero(allUsers)
        every { usersApiOperations.getAllUsers() } returns allUsers
    }

    private fun given_allTheUsersFoundOutsideRadius() {
        val allUsers = getAllUsersOutsideRadius()
        verify_allUsersHaveInitialDistanceOfZero(allUsers)
        every { usersApiOperations.getAllUsers() } returns allUsers
    }

    private fun given_allTheUsersFoundInRadius() {
        val usersForLocation = getUsersForLocation()
        verify_allUsersHaveInitialDistanceOfZero(usersForLocation)
        every { usersApiOperations.getAllCityUsers(LONDON_CITY) } returns usersForLocation
    }
}
package com.geolocate.citizens.entity.user

import com.geolocate.citizens.LOCATION_URL
import com.geolocate.citizens.USERS_URL
import com.geolocate.citizens.createUser
import com.geolocate.citizens.entity.user.response.UsersList
import io.kotlintest.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

class UsersApiOperationsTest {

    private val restTemplate: RestTemplate = mockk()
    private val usersApi: UsersApi = mockk()

    private val apiOperations = UsersApiOperations(restTemplate, usersApi)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `looking for all users should retrieve all regardless of location`() {
        given_callToFindAllUsers()
        val users = given_allResponseUsers()
        val response = apiOperations.getAllUsers()

        response shouldBe users.toSet()
    }

    @Test
    fun `retrieving for all users encountered connection issues`() {
        given_callToFindAllUsers_throwsException()

        val result = apiOperations.getAllUsers()

        result shouldBe emptySet()
    }

    @Test
    fun `retrieving users in location encountered connection issues`() {
        given_callToUsersInLocation_throwsException()

        val result = apiOperations.getAllUsers()

        result shouldBe emptySet()
    }

    private fun given_callToFindAllUsers_throwsException() {
        every { restTemplate.getForEntity(USERS_URL, UsersList::class.java) } throws IllegalStateException()
    }

    private fun given_callToUsersInLocation_throwsException() {
        every { restTemplate.getForEntity(USERS_URL, UsersList::class.java) } throws RestClientException("i felt flaky today")
    }

    private fun given_allResponseUsers(): UsersList {
        return mutableListOf(
                createUser(firstName = "first", latitude = 52352.52, longitude = -034235.32, distance = 3000.342),
                createUser(firstName = "second", latitude = -552.52, longitude = 3425.32, distance = 700.342),
                createUser(firstName = "third", latitude = -553422.52, longitude = -343425.32, distance = 70.342)
        ) as UsersList
    }

    private fun given_responseUsersInLocation(): UsersList {
        return mutableListOf(
                createUser(firstName = "first", latitude = 52352.52, longitude = -034235.32, city = "Manchester", distance = 3000.342),
                createUser(firstName = "second", latitude = -552.52, longitude = 3425.32, city = "Manchester", distance = 700.342),
                createUser(firstName = "third", latitude = -553422.52, longitude = -343425.32, city = "Manchester", distance = 70.342)
        ) as UsersList
    }

    private fun given_callToFindAllUsers() {
        val response = ResponseEntity.ok(given_allResponseUsers())
        every { restTemplate.getForEntity(USERS_URL, UsersList::class.java) } returns response
    }

    private fun given_callToFindUsersInLocation() {
        val response = ResponseEntity.ok(given_responseUsersInLocation())
        every { restTemplate.getForEntity(LOCATION_URL, UsersList::class.java) } returns response
    }
}
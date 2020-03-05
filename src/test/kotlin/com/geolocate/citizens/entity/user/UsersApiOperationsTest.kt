package com.geolocate.citizens.entity.user

import com.geolocate.citizens.USERS_URL
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
    private val usersApi: UsersApi = mockk(relaxed = true)

    private val apiOperations = UsersApiOperations(restTemplate, usersApi)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `looking for all users should retrieve all regardless of location`() {
        val users = given_callToFindAllUsers()
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

    private fun given_callToFindAllUsers(): UsersList {
        val response = ResponseEntity.ok(UsersList())
        every { restTemplate.getForEntity(USERS_URL, UsersList::class.java) } returns response
        return UsersList()
    }
}
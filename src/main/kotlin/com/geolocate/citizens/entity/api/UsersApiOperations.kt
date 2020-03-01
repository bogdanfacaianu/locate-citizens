package com.geolocate.citizens.entity.api

import com.geolocate.citizens.entity.response.User
import com.geolocate.citizens.entity.response.UsersList
import mu.KotlinLogging
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger {}

@Component
class UsersApiOperations(
        private val restTemplate: RestTemplate,
        private val usersApi: UsersApi
) {


    fun getUserDataById(userId: String): User? {
        return try {
            val userUrl = usersApi.toUserUrl(userId)
            val response = restTemplate.getForEntity(userUrl, User::class.java)
            return when(response.statusCode) {
                OK -> response.body
                else -> null
            }
        } catch (ex: Exception) {
            logger.error { "Fetching user with id $userId failed with error ${ex.message}" }
            null
        }
    }

    fun getAllUsers(): Collection<User> {
        return try {
            val usersUrl = usersApi.toAllUsersUrl()
            val response = restTemplate.getForEntity(usersUrl, UsersList::class.java)
            return when(response.statusCode) {
                OK -> response.body?.toSet() ?: emptySet()
                else -> emptySet()
            }
        } catch (ex: Exception) {
            logger.error { "Fetching all users failed with error ${ex.message}" }
            emptySet()
        }
    }

    fun getAllCityUsers(location: String): Collection<User> {
        return try {
            val usersUrl = usersApi.toLocationUrl(location)
            val response = restTemplate.getForEntity(usersUrl, UsersList::class.java)
            return when(response.statusCode) {
                OK -> response.body?.updateUsersCity(location) ?: emptySet()
                NOT_FOUND -> emptySet()
                else -> emptySet()
            }
        } catch (ex: Exception) {
            logger.error { "Fetching all users from $location failed with error ${ex.message}" }
            emptySet()
        }
    }
}
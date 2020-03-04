package com.geolocate.citizens.entity.user

import com.geolocate.citizens.entity.user.response.User
import com.geolocate.citizens.entity.user.response.UsersList
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
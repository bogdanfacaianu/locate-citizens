package com.geolocate.citizens.entity.api

import com.geolocate.citizens.entity.response.CityUser
import com.geolocate.citizens.entity.response.CityUsersList
import com.geolocate.citizens.entity.response.User
import com.geolocate.citizens.entity.response.UsersList
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger {}

@Component
class ApiRestOperations(
        private val restTemplate: RestTemplate,
        private val api: Api,
        @Value("\${baseuri}") private val baseUrl: String
) {


    fun getUserDataById(id: String): User? {
        return try {
            val userUrl = "$baseUrl/user/$id"
            val response = restTemplate.getForEntity(userUrl, User::class.java)
            return when(response.statusCode) {
                OK -> response.body
                NOT_FOUND -> null
                else -> null
            }
        } catch (ex: Exception) {
           null
        }
    }

    fun getAllUsers(): List<CityUser> {
        return try {
            val usersUrl = "$baseUrl/users"
            val response = restTemplate.getForEntity(usersUrl, CityUsersList::class.java)
            return when(response.statusCode) {
                OK -> response.body?.toUsersList() ?: emptyList()
                NOT_FOUND -> emptyList()
                else -> emptyList()
            }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    fun getInstructions(): String? {
        return try {
            val userUrl = "$baseUrl/instructions"
            val response = restTemplate.getForEntity(userUrl, String::class.java)
            return when(response.statusCode) {
                OK -> response.body
                NOT_FOUND -> null
                else -> null
            }
        } catch (ex: Exception) {
            null
        }
    }

    fun getAllCityUsers(city: String): List<User> {
        return try {
            val usersUrl = "$baseUrl/city/$city/users"
            val response = restTemplate.getForEntity(usersUrl, UsersList::class.java)
            return when(response.statusCode) {
                OK -> response.body?.toUsersList() ?: emptyList()
                NOT_FOUND -> emptyList()
                else -> emptyList()
            }
        } catch (ex: Exception) {
            emptyList()
        }
    }
}
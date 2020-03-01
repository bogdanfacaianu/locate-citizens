package com.geolocate.citizens.service

import com.geolocate.citizens.entity.api.ApiRestOperations
import org.springframework.stereotype.Service

@Service
class UserService(
        private val apiRestOperations: ApiRestOperations,
        private val jsonConverter: JsonConverter
) {

    fun getUsersDataForLocation(city: String): String {
        val allCityUsers = apiRestOperations.getAllCityUsers(city)
        return jsonConverter.serialiseUsers(allCityUsers)
    }
}
package com.geolocate.citizens.service

import com.geolocate.citizens.entity.response.CityUser
import com.geolocate.citizens.entity.response.User
import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class JsonConverter(
        private val gson: Gson
) {

    fun serialiseUser(user: User): String {
        return gson.toJson(user)
    }

    fun serialiseUsers(users: List<User>): String {
        return gson.toJson(users)
    }

    fun serialiseCityUsers(cityUsers: List<CityUser>): String {
        return gson.toJson(cityUsers)
    }
}
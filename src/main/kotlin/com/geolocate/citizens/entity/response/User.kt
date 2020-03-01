package com.geolocate.citizens.entity.response

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
        @JsonProperty("id") val id: String = "",
        @JsonProperty("first_name") val firstName: String = "",
        @JsonProperty("last_name") val lastName: String = "",
        @JsonProperty("email") val email: String = "",
        @JsonProperty("ip_address") val ipAddress: String = "",
        @JsonProperty("latitude") val latitude: Double = 0.0,
        @JsonProperty("longitude") val longitude: Double = 0.0,
        @JsonProperty("city") val city: String = ""
)

class UsersList : MutableList<User> by ArrayList() {
    fun toUsersList(): List<User> {
        return this.toList()
    }
}
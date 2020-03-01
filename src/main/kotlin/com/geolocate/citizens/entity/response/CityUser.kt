package com.geolocate.citizens.entity.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CityUser(
        @JsonProperty("id") val id: String = "",
        @JsonProperty("first_name") val firstName: String = "",
        @JsonProperty("last_name") val lastName: String = "",
        @JsonProperty("email") val email: String = "",
        @JsonProperty("ip_address") val ipAddress: String = "",
        @JsonProperty("latitude") val latitude: Double = 0.0,
        @JsonProperty("longitude") val longitude: Double = 0.0
)

class CityUsersList : MutableList<CityUser> by ArrayList() {
    fun toUsersList(): List<CityUser> {
        return this.toList()
    }
}


package com.geolocate.citizens.entity.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.geolocate.citizens.entity.Coordinates

data class User(
        @JsonProperty("id") val id: String = "",
        @JsonProperty("first_name") val firstName: String = "",
        @JsonProperty("last_name") val lastName: String = "",
        @JsonProperty("email") val email: String = "",
        @JsonProperty("ip_address") val ipAddress: String = "",
        @JsonProperty("latitude") val latitude: Double = 0.0,
        @JsonProperty("longitude") val longitude: Double = 0.0,
        @JsonProperty("city") val city: String = "",
        val distance: Double = 0.0  // in miles
) {
    fun updateCity(updatedCity: String) = this.copy(city = updatedCity)

    fun updateDistance(updatedDistance: Double) = this.copy(distance = updatedDistance)

    fun toCoordinates() = Coordinates(this.latitude, this.longitude)
}

class UsersList : MutableList<User> by ArrayList() {
    fun updateUsersCity(city: String): Set<User> {
        return this.map { it.updateCity(city) }.toSet()
    }
}
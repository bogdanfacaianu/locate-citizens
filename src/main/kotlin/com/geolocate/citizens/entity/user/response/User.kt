package com.geolocate.citizens.entity.user.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.geolocate.citizens.entity.coordinates.Coordinates

data class User(
        @JsonProperty("id") val id: String = "",
        @JsonProperty("first_name") val firstName: String = "",
        @JsonProperty("last_name") val lastName: String = "",
        @JsonProperty("email") val email: String = "",
        @JsonProperty("ip_address") val ipAddress: String = "",
        @JsonProperty("latitude") val latitude: Double = 0.0,
        @JsonProperty("longitude") val longitude: Double = 0.0,
        @JsonProperty("city") val location: String = "",
        val distance: Double = 0.0  // in miles
) {
    fun updateLocation(updatedLocation: String) = this.copy(location = updatedLocation)

    fun setDistanceAndLocation(updatedDistance: Double, updatedLocation: String)
            = this.copy(distance = updatedDistance, location = updatedLocation)

    fun toCoordinates() = Coordinates(this.latitude, this.longitude)
}

class UsersList : MutableList<User> by ArrayList() {

    fun updateUsersCity(location: String): Set<User> = this.map { it.updateLocation(location) }.toSet()

}
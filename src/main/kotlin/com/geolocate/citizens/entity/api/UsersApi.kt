package com.geolocate.citizens.entity.api

data class UsersApi(
        val url: String
) {
    fun toLocationUrl(location: String) = "${this.url}/city/$location/users"

    fun toUserUrl(userId: String) = "$this.url/user/$userId"

    fun toAllUsersUrl() = "${this.url}/users"
}
package com.geolocate.citizens

import com.geolocate.citizens.entity.coordinates.Coordinates
import com.geolocate.citizens.entity.user.response.User
import io.kotlintest.shouldBe

internal const val USERS_URL = "/allUsers"
internal const val LOCATION_URL = "/city/"
internal const val ID = "id"
internal const val FIRST_NAME = "firstName"
internal const val LAST_NAME = "lastName"
internal const val IP = "127.0.0.1"
internal const val EMAIL = "test@email.com"
internal const val LATITUDE = 0.0
internal const val LONGITUDE = 0.0
internal const val LONDON_LATITUDE = 51.5074
internal const val LONDON_LONGITUDE = 0.1278
internal const val CITY = "London"
internal const val DISTANCE = 50.0
internal const val COUNTRY_CODE = "UK"
internal val LONDON_COORDINATES = Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)

internal fun createUser(
        id: String = ID,
        firstName: String = FIRST_NAME,
        lastName: String = LAST_NAME,
        ip: String = IP,
        email: String = EMAIL,
        latitude: Double = LATITUDE,
        longitude: Double = LONGITUDE,
        city: String = CITY,
        distance: Double = DISTANCE
) = User(id, firstName, lastName, ip, email, latitude, longitude, city, distance)

internal fun getUsersForLocation(): Set<User> {
    return setOf(
            createUser(firstName = "first", latitude = 51.396910, longitude = -0.241831, city = CITY, distance = 0.0),
            createUser(firstName = "second", latitude = 51.443048, longitude = -0.986950, city = CITY, distance = 0.0),
            createUser(firstName = "third", latitude = 51.624178, longitude = -0.006850, city = CITY, distance = 0.0)
    )
}

internal fun getAllUsers(): Set<User> {
    return setOf(
            createUser(firstName = "first", latitude = 53.408675, longitude = -2.370587, city = "Manchester", distance = 0.0),
            createUser(firstName = "fourth", latitude = 51.484830, longitude = -0.1078991, city = CITY, distance = 0.0),
            createUser(firstName = "third", latitude = 39.848988, longitude = 116.412549, city = "Beijing", distance = 0.0),
            createUser(firstName = "fifth", latitude = -26.186910, longitude = 28.077102, city = "Johannesburg", distance = 0.0)
    )
}

internal fun getAllLondonUsers(): Set<User> {
    return setOf(
            createUser(firstName = "first", latitude = 51.396910, longitude = -0.241831, city = CITY, distance = 10.260782348426675),
            createUser(firstName = "second", latitude = 51.443048, longitude = -0.986950, city = CITY, distance = 17.656317391462466),
            createUser(firstName = "third", latitude = 51.624178, longitude = -0.006850, city = CITY, distance = 48.19199105586403),
            createUser(firstName = "fourth", latitude = 51.484830, longitude = -0.1078991, city = CITY, distance = 9.929874611895507)
    )
}

internal fun getAllUsersOutsideRadius(): Set<User> {
    return setOf(
            createUser(firstName = "first", latitude = 53.408675, longitude = -2.370587, city = "Manchester", distance = 0.0),
            createUser(firstName = "second", latitude = 39.848988, longitude = 116.412549, city = "Beijing", distance = 0.0),
            createUser(firstName = "third", latitude = -26.186910, longitude = 28.077102, city = "Johannesburg", distance = 0.0)
    )
}

internal fun verify_allUsersHaveInitialDistanceOfZero(users: Collection<User>) = users.all { it.distance == 0.0 }

internal fun verify_allUsersHaveUpdatedDistances(users: Collection<User>) = users.all { it.distance > 0.0 }

internal fun verify_allLondonUsersHaveBeenRetrieved(users: Collection<User>) {
    val londonUsers = getAllLondonUsers()
    users.size shouldBe londonUsers.size
    verify_allUsersHaveUpdatedDistances(londonUsers)
}
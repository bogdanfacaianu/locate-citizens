package com.geolocate.citizens.entity.location

import com.geolocate.citizens.entity.coordinates.Coordinates
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class GeoLocationCache(
        private val geoLocationFinder: GeoLocationFinder
) {

    private val cache: HashMap<String, GeoLocation> = HashMap()

    fun getGeoLocation(location: String, countryCode: String): GeoLocation? {
        val key = buildCacheKey(location, countryCode)
        if (!cache.containsKey(key)) {
            val coordinates = retrieveLocationCoordinates(location, countryCode)
            return coordinates?.let {
                val geoLocation = GeoLocation(key, location, countryCode, it)
                saveLocation(geoLocation)
                return geoLocation
            }
        }
        return retrieveFromCache(key)
    }

    fun retrieveFromCache(key: String) = cache[key]

    private fun saveLocation(geoLocation: GeoLocation) {
        logger.info { "Caching value $geoLocation" }
        cache[geoLocation.key] = geoLocation
    }

    fun clear() {
        cache.clear()
    }

    private fun retrieveLocationCoordinates(location: String, countryCode: String?): Coordinates? {
        return geoLocationFinder.getCoordinates(location, countryCode)
    }

    private fun buildCacheKey(location: String, countryCode: String) = String.format("%s_%s", location, countryCode)
}

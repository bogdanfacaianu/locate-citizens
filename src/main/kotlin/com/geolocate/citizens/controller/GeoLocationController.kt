package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.location.GeoLocationCache
import com.geolocate.citizens.entity.location.GeoLocationFinder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GeoLocationController(
        private val geoLocationFinder: GeoLocationFinder,
        private val geoLocationCache: GeoLocationCache
) {

    @GetMapping("/getGeoCoordinates/{location}")
    fun getGeoCoordinates(@PathVariable location: String): Any? {
        return geoLocationFinder.getCoordinates(location)
    }

    @DeleteMapping("/clearCache")
    fun clearGeoLocationCache() {
        geoLocationCache.clear()
    }

}
package com.geolocate.citizens.controller

import com.geolocate.citizens.entity.api.GeoLocationFinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GeoLocationController(
        private val geoLocationFinder: GeoLocationFinder
) {

    @GetMapping("/getGeoCoordinates/{location}")
    fun getGeoCoordinates(@PathVariable location: String): Any? {
        return geoLocationFinder.getCoordinates(location)
    }


}
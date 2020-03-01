package com.geolocate.citizens.configuration

import com.byteowls.jopencage.JOpenCageGeocoder
import com.geolocate.citizens.entity.api.UsersApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
class ApiConfiguration {

    @Bean
    fun getGeoApi(@Value("\${users.api.baseuri}") baseUri: String
    ): UsersApi = UsersApi(baseUri)

    @Bean
    fun getJOpenCageGeocoder(@Value("\${geolocator.api.key}") key: String
    ): JOpenCageGeocoder = JOpenCageGeocoder(key)
}
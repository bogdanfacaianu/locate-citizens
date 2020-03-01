package com.geolocate.citizens.configuration

import com.geolocate.citizens.entity.api.Api
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfiguration {

    @Bean
    fun testDataApi(): Api {
        return Api("https://bpdts-test-app.herokuapp.com")
    }
}
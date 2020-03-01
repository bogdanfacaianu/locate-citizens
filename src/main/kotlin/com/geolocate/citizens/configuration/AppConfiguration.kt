package com.geolocate.citizens.configuration

import com.google.gson.GsonBuilder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@ComponentScan
@Configuration
class AppConfiguration {

    @Bean
    fun gsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }

    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate? {
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(2L)).build()
    }

}
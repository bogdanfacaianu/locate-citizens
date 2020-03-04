package com.geolocate.citizens.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.geolocate.citizens.CITY
import com.geolocate.citizens.COUNTRY_CODE
import com.geolocate.citizens.DISTANCE
import com.geolocate.citizens.createUser
import com.geolocate.citizens.getAllLondonUsers
import com.geolocate.citizens.service.UsersLocationService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class UsersControllerTest {

    private val usersLocationService: UsersLocationService = mockk(relaxed = true)
    private val controller = UsersController(usersLocationService)
    private var mockMvc: MockMvc = standaloneSetup(controller).build()

    private val objectMapper = ObjectMapper()

    @Before
    fun setUp() {
        clearAllMocks()
        mockkStatic("com.geolocate.citizens.service.UsersLocationServiceKt")
    }

    @Test
    fun `when request comes with no parameters, use default and return users found`() {
        val distance = "30.0"
        val response = given_requestReturnsResults()

        mockMvc.perform(get("/getAllUsersInLocation")
                .param("location", CITY)
                .param("countryCode", COUNTRY_CODE)
                .param("distance", distance))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(response))

        verify { usersLocationService.getUserDataInLocation(CITY, distance.toDouble(), COUNTRY_CODE) }
    }

    @Test
    fun `when no parameters given default ones are considered and request returns successfully`() {
        val response = given_requestReturnsResults()
        mockMvc.perform(get("/getAllUsersInLocation"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(response))

        verify { usersLocationService.getUserDataInLocation(CITY, DISTANCE, COUNTRY_CODE) }
    }

    private fun given_requestReturnsResults(): String {
        val users = setOf(createUser())
        val response = objectMapper.writeValueAsString(users)

        every { usersLocationService.getUserDataInLocation(CITY, DISTANCE, COUNTRY_CODE) } returns getAllLondonUsers()

        return response
    }
}
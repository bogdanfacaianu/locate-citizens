package com.geolocate.citizens.entity.coordinates

import com.geolocate.citizens.LONDON_LATITUDE
import com.geolocate.citizens.LONDON_LONGITUDE
import io.kotlintest.matchers.doubles.shouldBeGreaterThan
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class CoordinatesTest {

    @Test
    fun `test distance is calculated between coordinates far from each other`() {
        val source = Coordinates(-26.186910,28.077102)
        val destination = Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)

        source.computeDistanceFrom(destination) shouldBeGreaterThan 0.0
    }

    @Test
    fun `test distance is 0 when coordinates point to the same location`() {
        val source = Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)
        val destination = Coordinates(LONDON_LATITUDE, LONDON_LONGITUDE)

        source.computeDistanceFrom(destination) shouldBe 0.0
    }

}
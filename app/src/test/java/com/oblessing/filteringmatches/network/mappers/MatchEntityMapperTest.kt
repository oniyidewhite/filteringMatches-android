package com.oblessing.filteringmatches.network.mappers

import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.models.FilterDistance
import com.oblessing.filteringmatches.models.LatLng
import com.oblessing.filteringmatches.models.Range
import com.oblessing.filteringmatches.network.CityEntity
import com.oblessing.filteringmatches.network.MatchEntity
import com.oblessing.filteringmatches.network.MatchResponseEntity
import org.junit.Test

class MatchEntityMapperTest {
    @Test
    fun `check Filter convert to MatchRequestEntity successfully`() {
        val mapper = MatchEntityMapper()

        val request = Filter(
            inContact = true,
            favourite = true,
            hasPhoto = false,
            ageRange = Range(10, 100),
            compatibility = Range(10, 100),
            heightRange = Range(100, 300),
            distanceInKm = FilterDistance(LatLng(1.0, -1.0), Range(10, 100))
        )

        val response = mapper.convert(request)

        assert(request.inContact == response.inContact)
        assert(request.favourite == response.favourite)
        assert(request.hasPhoto == response.hasPhoto)
        assert(request.ageRange.from == response.ageRange.from && request.ageRange.to == response.ageRange.to)
        assert(request.compatibility.from == response.compatibility.from && request.compatibility.to == response.compatibility.to)
        assert(request.heightRange.from == response.heightRange.from && request.heightRange.to == response.heightRange.to)
        assert(request.distanceInKm.location.lat == response.distanceInKm.lat && request.distanceInKm.location.lng == response.distanceInKm.lng && request.distanceInKm.range.from == response.distanceInKm.from && request.distanceInKm.range.to == response.distanceInKm.to)
    }

    @Test
    fun `check  MatchResponseEntity converts to List of Match`() {
        val mapper = MatchEntityMapper()

        // test null
        var response = mapper.convert(entity = null)
        assert(response == null)

        val request = MatchResponseEntity(
            result = listOf(
                MatchEntity(
                    displayName = "tmp",
                    age = 10,
                    jobTitle = "tmp",
                    distance = 1.0,
                    contactsExchanged = 1,
                    mainPhoto = "tmp",
                    favourite = true,
                    heightInCm = 200,
                    compatibilityScore = 0.9,
                    city = CityEntity("tmp")
                )
            )
        )

        response = mapper.convert(entity = request)

        assert(request.result!![0].displayName == response!![0].displayName)
        assert("${request.result!![0].age} years" == response[0].age)
        assert(request.result!![0].jobTitle == response[0].jobTitle)
        assert("${String.format("%.2f", request.result!![0].distance)} Km" == response[0].distance)
        assert(request.result!![0].contactsExchanged > 0 == response[0].inContact)
        assert(request.result!![0].mainPhoto == response[0].imageUrl)
        assert(request.result!![0].favourite == response[0].favourite)
        assert("${request.result!![0].heightInCm} cm" == response[0].height)
        assert(request.result!![0].compatibilityScore == response[0].score)
        assert(request.result!![0].city.name == response[0].city)
    }
}
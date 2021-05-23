package com.oblessing.filteringmatches.network.graphql.mappers

import FindMatchQuery
import com.oblessing.filteringmatches.network.EntityFilterDistance
import com.oblessing.filteringmatches.network.EntityRange
import com.oblessing.filteringmatches.network.MatchRequestEntity
import org.junit.Test

class GraphqlEntityMapperTest {
    @Test
    fun `test convert for MatchRequestEntity to InputFilter`() {
        val mapper = GraphqlEntityMapper()

        val request = MatchRequestEntity(
            hasPhoto = true,
            inContact = false,
            favourite = true,
            compatibility = EntityRange(10, 100),
            ageRange = EntityRange(10, 80),
            heightRange = EntityRange(120, 400),
            distanceInKm = EntityFilterDistance(10, 100, 0.1, -1.0)
        )

        val result = mapper.convert(request)

        assert(result.favourite == request.favourite)
        assert(result.inContact == request.inContact)
        assert(result.hasPhoto == request.hasPhoto)
        assert(result.compatibilityScore.value?.from == request.compatibility.from && result.compatibilityScore.value?.to == request.compatibility.to)
        assert(result.age.value?.from == request.ageRange.from && result.age.value?.to == request.ageRange.to)
        assert(result.height.value?.from == request.heightRange.from && result.height.value?.to == request.heightRange.to)
        assert(result.distanceInKm.from == request.distanceInKm.from && result.distanceInKm.to == request.distanceInKm.to && result.distanceInKm.lat == request.distanceInKm.lat && result.distanceInKm.lng == request.distanceInKm.lng)
    }

    @Test
    fun `test convert for list of FindMatchQueryMatch to MatchResponseEntity`() {
        val mapper = GraphqlEntityMapper()

        // test that null works as expected
        var response = mapper.convert(null)
        assert(response.result == null)

        // try with data
        val request = listOf(
            FindMatchQuery.Match(
                displayName = "tmp",
                age = 10,
                favourite = true,
                contactsExchanged = 10,
                jobTitle = "tmp",
                mainPhoto = "tmp",
                compatibilityScore = 1.0,
                distance = 10.0,
                heightInCm = 10,
                city = FindMatchQuery.City(name = "tmp")
            )
        )

        response = mapper.convert(request)

        assert(request[0].favourite == response.result!![0].favourite)
        assert(request[0].displayName == response.result!![0].displayName)
        assert(request[0].age == response.result!![0].age)
        assert(request[0].contactsExchanged == response.result!![0].contactsExchanged)
        assert(request[0].jobTitle == response.result!![0].jobTitle)
        assert(request[0].mainPhoto == response.result!![0].mainPhoto)
        assert(request[0].compatibilityScore == response.result!![0].compatibilityScore)
        assert(request[0].distance == response.result!![0].distance)
        assert(request[0].heightInCm == response.result!![0].heightInCm)
        assert(request[0].city.name == response.result!![0].city.name)
    }
}
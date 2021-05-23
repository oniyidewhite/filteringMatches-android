package com.oblessing.filteringmatches.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.models.FilterDistance
import com.oblessing.filteringmatches.models.Range
import com.oblessing.filteringmatches.network.CityEntity
import com.oblessing.filteringmatches.network.MatchEntity
import com.oblessing.filteringmatches.network.MatchResponseEntity
import com.oblessing.filteringmatches.network.WebService
import com.oblessing.filteringmatches.network.mappers.MatchEntityMapper
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MatchRepositoryTest {
    private lateinit var webService: WebService
    private lateinit var mapper: MatchEntityMapper

    @Before
    fun setup() {
        webService = mock()
        mapper = MatchEntityMapper()
    }

    @InternalCoroutinesApi
    @Test
    fun `test match repository for valid response`(): Unit = runBlocking {
        val filter = Filter(
            true,
            true,
            true,
            Range.defaultCompatibilityRange,
            Range.defaultAgeRange,
            Range.defaultHeightRange,
            FilterDistance.defaultFilterDistance
        )
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
        whenever(webService.findMatches(mapper.convert(filter))).thenReturn(request)
        val repository = MatchRepository(webService, mapper)
        repository.findMatches(filter).collect { response ->
            assert(response != null)

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

    @InternalCoroutinesApi
    @Test
    fun `test match repository for failed response`(): Unit = runBlocking {
        val filter = Filter(
            true,
            true,
            true,
            Range.defaultCompatibilityRange,
            Range.defaultAgeRange,
            Range.defaultHeightRange,
            FilterDistance.defaultFilterDistance
        )
        whenever(webService.findMatches(mapper.convert(filter))).thenReturn(null)
        val repository = MatchRepository(webService, mapper)
        repository.findMatches(filter).collect { response ->
            assert(response == null)
        }
    }

    @InternalCoroutinesApi
    @Test
    fun `test match repository for empty response`(): Unit = runBlocking {
        val filter = Filter(
            true,
            true,
            true,
            Range.defaultCompatibilityRange,
            Range.defaultAgeRange,
            Range.defaultHeightRange,
            FilterDistance.defaultFilterDistance
        )

        whenever(webService.findMatches(mapper.convert(filter))).thenReturn(MatchResponseEntity(result = listOf()))
        val repository = MatchRepository(webService, mapper)
        repository.findMatches(filter).collect { response ->
            assert(response != null)
            assert(response!!.isEmpty())
        }
    }
}
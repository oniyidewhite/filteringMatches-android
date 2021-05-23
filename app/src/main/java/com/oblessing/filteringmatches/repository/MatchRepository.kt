package com.oblessing.filteringmatches.repository

import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.models.Match
import com.oblessing.filteringmatches.network.WebService
import com.oblessing.filteringmatches.network.mappers.MatchEntityMapper
import kotlinx.coroutines.flow.flow

class MatchRepository
constructor(private val webService: WebService, private val mapper: MatchEntityMapper) {
    fun findMatches(filter: Filter) = flow<List<Match>?> {
        val result = webService.findMatches(mapper.convert(filter))
        emit(mapper.convert(result))
    }
}
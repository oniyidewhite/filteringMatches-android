package com.oblessing.filteringmatches.network

interface WebService {
    suspend fun findMatches(filterRequest: MatchRequestEntity): MatchResponseEntity?
}
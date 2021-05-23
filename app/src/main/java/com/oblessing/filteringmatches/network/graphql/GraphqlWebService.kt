package com.oblessing.filteringmatches.network.graphql

import FindMatchQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.oblessing.filteringmatches.network.MatchRequestEntity
import com.oblessing.filteringmatches.network.MatchResponseEntity
import com.oblessing.filteringmatches.network.WebService
import com.oblessing.filteringmatches.network.graphql.mappers.GraphqlEntityMapper

class GraphqlWebService(
    private val client: ApolloClient,
    private val mapper: GraphqlEntityMapper
) : WebService {
    override suspend fun findMatches(filterRequest: MatchRequestEntity): MatchResponseEntity {
        val result = client.query(FindMatchQuery(mapper.convert(filterRequest))).await()
        return mapper.convert(result.data?.matches)
    }
}
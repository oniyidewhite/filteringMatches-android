package com.oblessing.filteringmatches.network.graphql.mappers

import com.apollographql.apollo.api.Input
import com.oblessing.filteringmatches.network.CityEntity
import com.oblessing.filteringmatches.network.MatchEntity
import com.oblessing.filteringmatches.network.MatchRequestEntity
import com.oblessing.filteringmatches.network.MatchResponseEntity
import type.InputFilter
import type.LatLngRange
import type.Range

class GraphqlEntityMapper {
    fun convert(domain: MatchRequestEntity): InputFilter {
        return InputFilter(
            hasPhoto = domain.hasPhoto,
            inContact = domain.inContact,
            favourite = domain.favourite,
            compatibilityScore = Input.optional(
                Range(
                    domain.compatibility.from,
                    domain.compatibility.to
                )
            ),
            age = Input.optional(Range(domain.ageRange.from, domain.ageRange.to)),
            height = Input.optional(Range(domain.heightRange.from, domain.heightRange.to)),
            distanceInKm = LatLngRange(
                domain.distanceInKm.lat,
                domain.distanceInKm.lng,
                domain.distanceInKm.from,
                domain.distanceInKm.to
            )
        )
    }

    fun convert(list: List<FindMatchQuery.Match>?): MatchResponseEntity {
        return MatchResponseEntity(result = list?.map {
            MatchEntity(
                distance = it.distance,
                displayName = it.displayName,
                jobTitle = it.jobTitle,
                age = it.age,
                mainPhoto = it.mainPhoto,
                contactsExchanged = it.contactsExchanged,
                favourite = it.favourite,
                heightInCm = it.heightInCm,
                compatibilityScore = it.compatibilityScore,
                city = CityEntity(it.city.name)
            )
        })
    }
}
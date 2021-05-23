package com.oblessing.filteringmatches.network.mappers

import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.models.Match
import com.oblessing.filteringmatches.network.EntityFilterDistance
import com.oblessing.filteringmatches.network.EntityRange
import com.oblessing.filteringmatches.network.MatchRequestEntity
import com.oblessing.filteringmatches.network.MatchResponseEntity
import javax.inject.Inject

class MatchEntityMapper
@Inject constructor() {
    fun convert(domain: Filter): MatchRequestEntity {
        return MatchRequestEntity(
            hasPhoto = domain.hasPhoto,
            inContact = domain.inContact,
            compatibility = EntityRange(domain.compatibility.from, domain.compatibility.to),
            ageRange = EntityRange(domain.ageRange.from, domain.ageRange.to),
            heightRange = EntityRange(domain.heightRange.from, domain.heightRange.to),
            distanceInKm = EntityFilterDistance(
                domain.distanceInKm.range.from,
                domain.distanceInKm.range.to,
                domain.distanceInKm.location.lat,
                domain.distanceInKm.location.lng
            ),
            favourite = domain.favourite
        )
    }

    fun convert(entity: MatchResponseEntity?): List<Match>? {
        return entity?.result?.map {
            Match(
                distance = "${String.format("%.2f", it.distance)} Km",
                city = it.city.name,
                jobTitle = it.jobTitle,
                favourite = it.favourite,
                inContact = it.contactsExchanged > 0,
                age = "${it.age} years",
                displayName = it.displayName,
                height = "${it.heightInCm} cm",
                score = it.compatibilityScore,
                imageUrl = it.mainPhoto.toString()
            )
        }
    }
}
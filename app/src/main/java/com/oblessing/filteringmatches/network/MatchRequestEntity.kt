package com.oblessing.filteringmatches.network

data class MatchRequestEntity(
    val hasPhoto: Boolean,
    val inContact: Boolean,
    val favourite: Boolean,
    val compatibility: EntityRange,
    val ageRange: EntityRange,
    val heightRange: EntityRange,
    val distanceInKm: EntityFilterDistance
)

data class EntityRange(val from: Int, val to: Int)

data class EntityFilterDistance(val from: Int, val to: Int, val lat: Double, val lng: Double)

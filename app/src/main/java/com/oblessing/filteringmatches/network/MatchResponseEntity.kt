package com.oblessing.filteringmatches.network

data class MatchResponseEntity(val result: List<MatchEntity>?)

data class MatchEntity(
    val displayName: String,
    val age: Int,
    val jobTitle: String,
    val distance: Double,
    val mainPhoto: String?,
    val contactsExchanged: Int,
    val favourite: Boolean,
    val heightInCm: Int,
    val compatibilityScore: Double,
    val city: CityEntity
)

data class CityEntity(val name: String)


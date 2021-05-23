package com.oblessing.filteringmatches.models

data class Filter(
    val hasPhoto: Boolean,
    val inContact: Boolean,
    val favourite: Boolean,
    val compatibility: Range,
    val ageRange: Range,
    val heightRange: Range,
    val distanceInKm: FilterDistance
)
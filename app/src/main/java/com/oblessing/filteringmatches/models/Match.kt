package com.oblessing.filteringmatches.models

data class Match(
    val displayName: String,
    val imageUrl: String,
    val jobTitle: String,
    val distance: String,
    val city: String,
    val score: Double,
    val age: String,
    val height: String,
    val inContact: Boolean,
    val favourite: Boolean
)
package com.oblessing.filteringmatches.states

data class FilterDistance(val location: LatLng, val range: Range) {
    companion object {
        val defaultFilterDistance = FilterDistance(LatLng.defaultUserLocation, Range.defaultKmRange)
    }
}


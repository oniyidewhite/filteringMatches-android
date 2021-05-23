package com.oblessing.filteringmatches.models

data class LatLng(val lat: Double, val lng: Double) {
    companion object {
        // I assumed the user is at this location.
        // this assumption is made based on the assignment README
        // https://github.com/sparknetworks/coding_exercises_options/blob/master/filtering_matches/README.md
        val defaultUserLocation = LatLng(51.509865, -0.118092)
    }
}


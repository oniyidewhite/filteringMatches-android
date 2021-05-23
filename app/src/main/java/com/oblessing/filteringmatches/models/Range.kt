package com.oblessing.filteringmatches.models

data class Range(val from: Int, val to: Int) {
    companion object {
        val defaultCompatibilityRange = Range(1, 99)
        val defaultAgeRange = Range(18, 95)
        val defaultHeightAgeRange = Range(135, 210)
        val defaultKmRange = Range(30, 300)
    }
}


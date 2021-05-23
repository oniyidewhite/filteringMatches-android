package com.oblessing.filteringmatches.states

import com.oblessing.filteringmatches.models.FilterDistance
import com.oblessing.filteringmatches.models.LatLng
import com.oblessing.filteringmatches.models.Match
import com.oblessing.filteringmatches.models.Range
import org.junit.Test
import com.oblessing.filteringmatches.states.FilterMatchState.Event
import com.oblessing.filteringmatches.states.FilterMatchState.Effect

class FilterMatchStateTest {
    @Test
    fun `on user tapped find, effect should change to submit the filter request`() {
        val state = FilterMatchState().reduce(Event.UpdatedHasPhoto(false)).reduce(Event.TappedFind)

        assert(state.effect == Effect.SubmitRequest)
        assert(!state.hasPhoto)
    }

    @Test
    fun `filter state should be updated based on the user preference`() {
        val state = FilterMatchState()
            .reduce(Event.UpdatedHasPhoto(false))
            .reduce(Event.UpdatedInContact(false))
            .reduce(Event.UpdatedAgeRange(Range(10, 99)))
            .reduce(Event.UpdatedCompatibility(Range(80, 90)))
            .reduce(Event.UpdatedHeightRange(Range(10, 30)))
            .reduce(Event.UpdatedDistanceInKm(FilterDistance(LatLng(0.0, -1.0), Range(100, 1000))))

        assert(!state.hasPhoto)
        assert(!state.inContact)
        assert(state.ageRange.from == 10 && state.ageRange.to == 99)
        assert(state.compatibility.from == 80 && state.compatibility.to == 90)
        assert(state.heightRange.from == 10 && state.heightRange.to == 30)
        assert(
            state.distanceInKm.location.lat == 0.0 && state.distanceInKm.location.lng == -1.0 &&
                    state.distanceInKm.range.from == 100 && state.distanceInKm.range.to == 1000
        )
    }

    @Test
    fun `show progress should be true on submission`() {
        val state = FilterMatchState()
            .reduce(Event.TappedFind)

        assert(state.showProgress)
    }

    @Test
    fun `effect should output error on submission failure, show progress should be false`() {
        val state = FilterMatchState()
            .reduce(Event.TappedFind)
            .reduce(Event.RequestFailed)

        assert(!state.showProgress)
        assert(state.effect == Effect.ShowError)
    }

    @Test
    fun `matches should not be empty and show progress should not be false on successful response`() {
        val match = Match(
            displayName = "test",
            age = "20yr",
            height = "175cm",
            imageUrl = "",
            jobTitle = "n/a",
            favourite = false,
            inContact = false,
            city = "tmp",
            distance = "10km",
            score = 0.9
        )
        val state = FilterMatchState()
            .reduce(Event.TappedFind)
            .reduce(Event.LoadedMatches(listOf(match)))

        assert(!state.showProgress)
        assert(state.effect == null)
        assert(state.matches?.first() == match)
    }
}
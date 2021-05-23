package com.oblessing.filteringmatches.states

import com.airbnb.mvrx.MavericksState
import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.models.FilterDistance
import com.oblessing.filteringmatches.models.Match
import com.oblessing.filteringmatches.models.Range

data class FilterMatchState(
    val hasPhoto: Boolean = true,
    val inContact: Boolean = true,
    val favourite: Boolean = false,
    val compatibility: Range = Range.defaultCompatibilityRange,
    val ageRange: Range = Range.defaultAgeRange,
    val heightRange: Range = Range.defaultHeightAgeRange,
    val distanceInKm: FilterDistance = FilterDistance.defaultFilterDistance,

    val event: Event = Event.TappedReset,
    val effect: Effect? = Effect.SubmitRequest,

    val matches: List<Match>? = null,

    private val inProgress: Boolean = true,
) : MavericksState {
    val showProgress get() = inProgress

    fun reduce(e: Event): FilterMatchState {
        return when (e) {
            Event.TappedReset -> copy(
                event = e,
                hasPhoto = true,
                inContact = true,
                favourite = false,
                compatibility = Range.defaultCompatibilityRange,
                ageRange = Range.defaultAgeRange,
                heightRange = Range.defaultHeightAgeRange,
                distanceInKm = FilterDistance.defaultFilterDistance,
                effect = Effect.SubmitRequest,
                matches = null,
                inProgress = true
            )
            Event.HandledEffect -> copy(event = e, effect = null)

            Event.TappedFind -> copy(event = e, effect = Effect.SubmitRequest, inProgress = true)
            Event.RequestFailed -> copy(event = e, effect = Effect.ShowError, inProgress = false)
            is Event.LoadedMatches -> copy(
                event = e,
                matches = e.values,
                inProgress = false,
                effect = null
            )

            is Event.UpdatedHasPhoto -> copy(event = e, hasPhoto = e.value)
            is Event.UpdatedInContact -> copy(event = e, inContact = e.value)

            is Event.UpdatedCompatibility -> copy(event = e, compatibility = e.value)
            is Event.UpdatedAgeRange -> copy(event = e, ageRange = e.value)
            is Event.UpdatedHeightRange -> copy(event = e, heightRange = e.value)
            is Event.UpdatedDistanceInKm -> copy(event = e, distanceInKm = e.value)
            is Event.UpdatedFavourite -> copy(event = e, favourite = e.value)
        }
    }

    fun toFilter(): Filter {
        return Filter(hasPhoto, inContact, favourite, compatibility, ageRange, heightRange, distanceInKm)
    }


    sealed class Event {
        object TappedReset : Event()
        object HandledEffect : Event()

        object TappedFind : Event()
        object RequestFailed : Event()

        data class LoadedMatches(val values: List<Match>) : Event()

        data class UpdatedHasPhoto(val value: Boolean) : Event()
        data class UpdatedFavourite(val value: Boolean) : Event()
        data class UpdatedInContact(val value: Boolean) : Event()
        data class UpdatedCompatibility(val value: Range) : Event()
        data class UpdatedAgeRange(val value: Range) : Event()
        data class UpdatedHeightRange(val value: Range) : Event()
        data class UpdatedDistanceInKm(val value: FilterDistance) : Event()
    }

    sealed class Effect {
        object SubmitRequest : Effect()
        object ShowError : Effect()
    }
}


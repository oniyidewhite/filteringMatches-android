package com.oblessing.filteringmatches.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.oblessing.filteringmatches.core.support.AssistedViewModelFactory
import com.oblessing.filteringmatches.core.support.hiltMavericksViewModelFactory
import com.oblessing.filteringmatches.states.FilterMatchState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MatchListViewModel
@AssistedInject constructor(@Assisted state: FilterMatchState) :
    MavericksViewModel<FilterMatchState>(state) {
    fun loadMatches() {
        setState { reduce(FilterMatchState.Event.TappedFind) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MatchListViewModel, FilterMatchState> {
        override fun create(state: FilterMatchState): MatchListViewModel
    }

    companion object :
        MavericksViewModelFactory<MatchListViewModel, FilterMatchState> by hiltMavericksViewModelFactory()
}
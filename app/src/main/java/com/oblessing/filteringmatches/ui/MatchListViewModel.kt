package com.oblessing.filteringmatches.ui

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.oblessing.filteringmatches.core.support.AssistedViewModelFactory
import com.oblessing.filteringmatches.core.support.hiltMavericksViewModelFactory
import com.oblessing.filteringmatches.models.Filter
import com.oblessing.filteringmatches.repository.MatchRepository
import com.oblessing.filteringmatches.states.FilterMatchState
import com.oblessing.filteringmatches.states.FilterMatchState.Effect
import com.oblessing.filteringmatches.states.FilterMatchState.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers.IO

class MatchListViewModel
@AssistedInject constructor(
    @Assisted state: FilterMatchState,
    private val repository: MatchRepository
) : MavericksViewModel<FilterMatchState>(state) {
    init {
        onEach(FilterMatchState::effect) { effect ->
            when (effect) {
                is Effect.SubmitRequest -> withState {
                    loadContent(it.toFilter())
                    postEvent(Event.HandledEffect)
                }
                else -> Unit
            }
        }
    }

    fun postEvent(event: Event) {
        setState { reduce(event) }
    }

    private fun loadContent(filter: Filter) {
        repository.findMatches(filter).execute(IO) { async ->
            when (async) {
                is Success -> {
                    reduce(Event.LoadedMatches(async() ?: emptyList()))
                }
                is Fail -> {
                    reduce(Event.RequestFailed)
                }
                else -> this
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MatchListViewModel, FilterMatchState> {
        override fun create(state: FilterMatchState): MatchListViewModel
    }

    companion object :
        MavericksViewModelFactory<MatchListViewModel, FilterMatchState> by hiltMavericksViewModelFactory()
}
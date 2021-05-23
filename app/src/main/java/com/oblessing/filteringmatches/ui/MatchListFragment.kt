package com.oblessing.filteringmatches.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar
import com.oblessing.filteringmatches.R
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.FragmentMatchListBinding
import com.oblessing.filteringmatches.states.FilterMatchState
import com.oblessing.filteringmatches.views.matchRow
import com.oblessing.filteringmatches.views.noContentRow

class MatchListFragment : Fragment(R.layout.fragment_match_list), MavericksView {
    private val binding: FragmentMatchListBinding by viewBinding()
    private val viewModel: MatchListViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        super.onViewCreated(view, savedInstanceState).also {
            binding.actionFilter.setOnClickListener {
                // Go to pref page
                navigateToPref()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState).also {
        viewModel.onEach(FilterMatchState::effect, FilterMatchState::showProgress) { effect, show ->
            // check if we should show progress
            binding.progress.isVisible = show

            // check if we need to display any errors
            when (effect) {
                FilterMatchState.Effect.ShowError -> {
                    showError()
                    viewModel.handledEffect()
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.recyclerView.withModels {
            // try showing content
            state.matches?.let { matches ->
                // check if we don't content
                if (matches.isEmpty()) {
                    noContentRow {
                        id("no-content")
                    }
                    return@withModels
                }

                // Add our matches
                matches.forEach {
                    matchRow {
                        id(it.displayName)
                        match(it)
                    }
                }
            }
        }
    }

    private fun navigateToPref() {
        findNavController().navigate(R.id.action_matchListFragment_to_matchPrefFragment)
    }

    private fun showError() {
        Snackbar.make(binding.root, R.string.unexpected_error, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.label_retry) {
                fetchMatches()
            }.show()
    }

    private fun fetchMatches() {
        // load content
        viewModel.loadMatches()
    }
}
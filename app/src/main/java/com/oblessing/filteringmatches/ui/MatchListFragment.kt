package com.oblessing.filteringmatches.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.oblessing.filteringmatches.R
import com.oblessing.filteringmatches.core.informationDialog
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.FragmentMatchListBinding
import com.oblessing.filteringmatches.models.LatLng
import com.oblessing.filteringmatches.states.FilterMatchState.Event
import com.oblessing.filteringmatches.states.FilterMatchState
import com.oblessing.filteringmatches.views.matchRow
import com.oblessing.filteringmatches.views.noContentRow

class MatchListFragment : Fragment(R.layout.fragment_match_list), MavericksView {
    private val binding: FragmentMatchListBinding by viewBinding()
    private val viewModel: MatchListViewModel by activityViewModel()

    private val locationRequestLauncher: ActivityResultLauncher<String> by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                saveUserLatLng()
            } else {
                showLocationRequestInfoToUser()
            }
        }
    }

    private fun tryUserLocation() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                // save user location
                saveUserLatLng()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> showLocationRequestInfoToUser()
            else -> requestLocationAccess()
        }
    }

    private val locationInfoRationalDialog: AlertDialog by lazy {
        informationDialog(
            requireContext(),
            getString(R.string.label_permission),
            getString(R.string.user_location_required),
            ::requestLocationAccess
        )
    }

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
                    postEvent(Event.HandledEffect)
                }
                else -> {
                    // Do nothing
                }
            }
        }

        locationRequestLauncher.apply {
            tryUserLocation()
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
                postEvent(Event.TappedFind)
            }.show()
    }

    private fun postEvent(event: Event) {
        viewModel.postEvent(event)
    }

    @SuppressLint("MissingPermission")
    private fun saveUserLatLng() {
        LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation.addOnSuccessListener {
            val latLng = if (it != null) LatLng(it.latitude, it.longitude) else LatLng.defaultUserLocation
            postEvent(Event.UpdatedDistanceInKmLocation(latLng))
        }
    }

    private fun showLocationRequestInfoToUser() {
        locationInfoRationalDialog.show()
    }

    private fun requestLocationAccess() {
        locationRequestLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}
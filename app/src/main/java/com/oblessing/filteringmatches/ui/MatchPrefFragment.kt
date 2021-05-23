package com.oblessing.filteringmatches.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.oblessing.filteringmatches.R
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.FragmentMatchPrefBinding
import com.oblessing.filteringmatches.models.Range
import com.oblessing.filteringmatches.states.FilterMatchState.Event
import com.oblessing.filteringmatches.views.*

class MatchPrefFragment : Fragment(R.layout.fragment_match_pref), MavericksView {
    private val viewModel: MatchListViewModel by activityViewModel()
    private val binding: FragmentMatchPrefBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        super.onViewCreated(view, savedInstanceState).also {
            binding.actionClose.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    private fun postEvent(event: Event) {
        viewModel.postEvent(event)
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.recyclerView.withModels {
            titleRow {
                id("title")
                text(R.string.label_filter_options)
            }

            subTitle {
                id("sub-title-age")
                text(R.string.label_age_range)
            }

            sliderRow {
                id("age-slider")
                value(
                    SliderRow.SliderOptions(
                        state.ageRange.from,
                        state.ageRange.to,
                        Range.defaultAgeRange.from,
                        Range.defaultAgeRange.to,
                        "Between %s years - %s years",
                    )
                )
                listener(object : SliderRow.CallBack {
                    override fun select(from: Int, to: Int) {
                        postEvent(Event.UpdatedAgeRange(Range(from, to)))
                    }
                })
            }

            subTitle {
                id("sub-title-score")
                text(R.string.label_compatibility_range)
            }

            sliderRow {
                id("compatibility-slider")
                value(
                    SliderRow.SliderOptions(
                        state.compatibility.from,
                        state.compatibility.to,
                        Range.defaultCompatibilityRange.from,
                        Range.defaultCompatibilityRange.to,
                        "Between %s%% - %s%%",
                    )
                )
                listener(object : SliderRow.CallBack {
                    override fun select(from: Int, to: Int) {
                        postEvent(Event.UpdatedCompatibility(Range(from, to)))
                    }
                })
            }

            subTitle {
                id("sub-title-height")
                text(R.string.label_height_range)
            }

            sliderRow {
                id("height-slider")
                value(
                    SliderRow.SliderOptions(
                        state.heightRange.from,
                        state.heightRange.to,
                        Range.defaultHeightRange.from,
                        Range.defaultHeightRange.to,
                        "Between %scm - %scm",
                    )
                )
                listener(object : SliderRow.CallBack {
                    override fun select(from: Int, to: Int) {
                        postEvent(Event.UpdatedHeightRange(Range(from, to)))
                    }
                })
            }

            subTitle {
                id("sub-title-distance")
                text(R.string.label_distance_range)
            }

            sliderRow {
                id("distance-slider")
                value(
                    SliderRow.SliderOptions(
                        state.distanceInKm.range.from,
                        state.distanceInKm.range.to,
                        Range.defaultKmRange.from,
                        Range.defaultKmRange.to,
                        "Between %sKm - %sKm",
                    )
                )
                listener(object : SliderRow.CallBack {
                    override fun select(from: Int, to: Int) {
                        postEvent(Event.UpdatedDistanceInKmRange(Range(from, to)))
                    }
                })
            }

            subTitle {
                id("sub-title-favourite")
                text(R.string.label_favourite)
            }

            radioBoxRow {
                id("favourite-yes")
                text(R.string.label_yes)
                checked(state.favourite)
                clickListener { _ ->
                    postEvent(Event.UpdatedFavourite(true))
                }
            }

            radioBoxRow {
                id("favourite-no")
                text(R.string.label_no)
                checked(!state.favourite)
                clickListener { _ ->
                    postEvent(Event.UpdatedFavourite(false))
                }
            }

            subTitle {
                id("sub-title-in-contact")
                text(R.string.label_in_contact)
            }

            radioBoxRow {
                id("in-contact-yes")
                text(R.string.label_yes)
                checked(state.inContact)
                clickListener { _ ->
                    postEvent(Event.UpdatedInContact(true))
                }
            }

            radioBoxRow {
                id("in-contact-no")
                text(R.string.label_no)
                checked(!state.inContact)
                clickListener { _ ->
                    postEvent(Event.UpdatedInContact(false))
                }
            }

            subTitle {
                id("sub-title-has-photo")
                text(R.string.label_has_photo)
            }

            radioBoxRow {
                id("has-photo-yes")
                text(R.string.label_yes)
                checked(state.hasPhoto)
                clickListener { _ ->
                    postEvent(Event.UpdatedHasPhoto(true))
                }
            }

            radioBoxRow {
                id("has-photo-no")
                text(R.string.label_no)
                checked(!state.hasPhoto)
                clickListener { _ ->
                    postEvent(Event.UpdatedHasPhoto(false))
                }
            }

            carousel {
                id("carousel")
                padding(Carousel.Padding.dp(0, 16))
                models(
                    listOf(
                        ButtonOutlineRowModel_().id("reset-preference").text(R.string.label_reset)
                            .listener { _ ->
                                postEvent(Event.TappedReset)
                            },
                        ButtonRowModel_().id("apply").text(R.string.label_apply)
                            .listener { _ ->
                                postEvent(Event.TappedFind)
                                findNavController().popBackStack()
                            }
                    )
                )
            }

        }
    }
}
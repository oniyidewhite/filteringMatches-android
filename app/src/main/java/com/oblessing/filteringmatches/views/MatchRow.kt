package com.oblessing.filteringmatches.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.oblessing.filteringmatches.R
import com.oblessing.filteringmatches.core.loadUrl
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.RowMatchBinding
import com.oblessing.filteringmatches.models.Match

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class MatchRow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {
    private val binding: RowMatchBinding by viewBinding()

    @ModelProp
    fun setMatch(match: Match) {
        binding.image.loadUrl(match.imageUrl)
        binding.name.text = match.displayName
        binding.ageAndCity.text = "${match.age} - ${match.city}"
        binding.score.setRating(match.score)
        binding.jobTitle.text = match.jobTitle
        binding.height.text = match.height
        binding.distance.text = match.distance
        binding.contactsExchanged.text = if (match.inContact) {
            context.getString(R.string.in_contact)
        } else {
            context.getString(R.string.not_in_contact)
        }
    }
}
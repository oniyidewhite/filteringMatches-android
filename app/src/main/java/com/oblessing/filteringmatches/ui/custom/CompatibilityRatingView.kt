package com.oblessing.filteringmatches.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.oblessing.filteringmatches.R
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.ViewRatingBinding

class CompatibilityRatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: ViewRatingBinding by viewBinding()

    fun setRating(value: Double) {
        val percentage = (value * 100).toInt()
        binding.indicatorView.max = 100
        binding.indicatorView.progress = percentage
        binding.indicatorView.isIndeterminate = false
        binding.valueView.text = "$percentage"

        if (percentage >= 50) {
            // paint green
            binding.indicatorView.background.setTint(ContextCompat.getColor(context, R.color.colorDarkGreen))
            binding.indicatorView.progressDrawable.setTint(ContextCompat.getColor(context, R.color.colorLightGreen))
        } else {
            // paint yellow
            binding.indicatorView.background.setTint(ContextCompat.getColor(context, R.color.colorDarkYellow))
            binding.indicatorView.progressDrawable.setTint(ContextCompat.getColor(context, R.color.colorLightYellow))
        }
    }
}
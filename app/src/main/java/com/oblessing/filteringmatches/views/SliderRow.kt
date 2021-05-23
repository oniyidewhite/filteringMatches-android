package com.oblessing.filteringmatches.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.RowSliderBinding
import io.apptik.widget.MultiSlider

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SliderRow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttr: Int = 0
) :
    FrameLayout(context, attributeSet, styleAttr) {
    private val binding: RowSliderBinding by viewBinding()
    private lateinit var options: SliderOptions

    @Volatile
    private var userInteraction = false
    private var callback: CallBack? = null

    @ModelProp
    fun setValue(options: SliderOptions) {
        if (userInteraction) return

        binding.slideView.max = options.maxValue - options.minValue
        binding.slideView.getThumb(0).value = options.valueFrom - options.minValue
        binding.slideView.getThumb(1).value = options.valueTo - options.minValue

        this.options = options
        updateDescription(options.valueFrom, options.valueTo)
    }


    private fun updateDescription(valueFrom: Int, valueTo: Int) {
        binding.textView.text = String.format(options.prefix, valueFrom, valueTo)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        callback = null
    }

    @CallbackProp
    fun setListener(callback: CallBack?) {
        this.callback = callback
        binding.slideView.setOnThumbValueChangeListener { multiSlider, _, _, _ ->
            val from = multiSlider.getThumb(0).value + options.minValue
            val to = multiSlider.getThumb(1).value + options.minValue
            callback?.select(from, to)
        }
    }

    interface CallBack {
        fun select(from: Int, to: Int)
    }

    data class SliderOptions(
        val valueFrom: Int,
        val valueTo: Int,
        val minValue: Int,
        val maxValue: Int,
        val prefix: String,
    )
}
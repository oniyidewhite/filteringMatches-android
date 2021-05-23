package com.oblessing.filteringmatches.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.RowRadioBoxBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RadioBoxRow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttr: Int = 0
) :
    FrameLayout(context, attributeSet, styleAttr) {
    private val binding: RowRadioBoxBinding by viewBinding()

    @TextProp
    fun setText(value: CharSequence?) {
        binding.checkboxView.text = value
    }

    @ModelProp
    fun setChecked(value: Boolean?) {
        binding.checkboxView.isChecked = value == true
    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        binding.checkboxView.setOnClickListener(listener)
    }
}
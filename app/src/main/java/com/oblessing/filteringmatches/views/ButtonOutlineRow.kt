package com.oblessing.filteringmatches.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.RowButtonOutlineBinding

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class ButtonOutlineRow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttr: Int = 0
) :
    FrameLayout(context, attributeSet, styleAttr) {
    private val binding: RowButtonOutlineBinding by viewBinding()

    @TextProp
    fun setText(value: CharSequence) {
        binding.textView.text = value
    }

    @CallbackProp
    fun setListener(listener: OnClickListener?) {
        binding.textView.setOnClickListener(listener)
    }
}
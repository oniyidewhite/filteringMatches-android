package com.oblessing.filteringmatches.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.oblessing.filteringmatches.core.mavericks.viewBinding
import com.oblessing.filteringmatches.databinding.RowTitleBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TitleRow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttr: Int = 0
) :
    FrameLayout(context, attributeSet, styleAttr) {
    private val binding: RowTitleBinding by viewBinding()

    @TextProp
    fun setText(value: CharSequence) {
        binding.textView.text = value
    }
}
package com.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.common.R
import com.google.android.flexbox.FlexboxLayout


/**
 * by y on 2017/5/18
 */

class FlowText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setPadding(18, 10, 18, 10)
        val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = 10
        params.bottomMargin = 10
        gravity = Gravity.CENTER
        textSize = 10f
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        layoutParams = params
        setBackgroundResource(R.drawable.shape_tag)
    }
}

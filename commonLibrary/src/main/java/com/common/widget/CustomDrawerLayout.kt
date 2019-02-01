package com.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

/**
 * by y on 2017/5/20.
 */

class CustomDrawerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : DrawerLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wmeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.EXACTLY)
        val hmeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), View.MeasureSpec.EXACTLY)
        super.onMeasure(wmeasureSpec, hmeasureSpec)
    }

}
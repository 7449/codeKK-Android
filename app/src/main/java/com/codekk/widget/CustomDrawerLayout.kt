package com.codekk.widget

import android.content.Context
import android.util.AttributeSet
import androidx.drawerlayout.widget.DrawerLayout

/**
 * by y on 2017/5/20.
 */

class CustomDrawerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : DrawerLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wmeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY)
        val hmeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY)
        super.onMeasure(wmeasureSpec, hmeasureSpec)
    }

}
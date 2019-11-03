@file:Suppress("DEPRECATION")

package com.codekk.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import com.mukesh.MarkdownView

/**
 * by y on 2017/5/17
 */
class SimpleMarkdownView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MarkdownView(context, attrs, defStyleAttr) {

    private var progressbar: ProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    var isLoading = false
        private set

    init {
        progressbar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 26, 0, 0)
        addView(progressbar)
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                newProgressBar(newProgress)
            }
        }
    }

    private fun newProgressBar(newProgress: Int) {
        if (newProgress == 100) {
            progressbar.visibility = View.GONE
            isLoading = true
        } else {
            if (progressbar.visibility == View.GONE) {
                progressbar.visibility = View.VISIBLE
            }
            isLoading = false
            progressbar.progress = newProgress
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressbar.layoutParams as LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }
}

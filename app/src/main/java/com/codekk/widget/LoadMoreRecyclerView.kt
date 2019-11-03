@file:Suppress("ClassName")

package com.codekk.widget

import android.content.Context
import android.util.AttributeSet

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * by y on 2017/5/16
 */

class LoadMoreRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {


    private var layoutManagerType: LAYOUT_MANAGER_TYPE? = null
    private var lastPositions: IntArray? = null
    private var lastVisibleItemPosition: Int = 0
    private var loadingListener: LoadMoreListener? = null

    fun setLoadingListener(loadingListener: LoadMoreListener) {
        this.loadingListener = loadingListener
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        val layoutManager = layoutManager
        if (layoutManagerType == null) {
            layoutManagerType = when (layoutManager) {
                is GridLayoutManager -> LAYOUT_MANAGER_TYPE.GRID
                is LinearLayoutManager -> LAYOUT_MANAGER_TYPE.LINEAR
                is StaggeredGridLayoutManager -> LAYOUT_MANAGER_TYPE.STAGGERED_GRID
                else -> throw RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }
        when (layoutManagerType) {
            LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastPositions?.let {
                    lastVisibleItemPosition = findMax(it)
                }
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        val layoutManager = layoutManager
        val visibleItemCount = layoutManager?.childCount ?: -1
        val totalItemCount = layoutManager?.itemCount ?: -1
        if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1) {
            loadingListener?.onLoadMore()
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    private enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }
}
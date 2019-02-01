package com.codekk.ui.base

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.backlayout.SwipeBackActivity
import com.backlayout.SwipeBackLayout
import com.codekk.App
import com.codekk.R
import com.status.layout.OnStatusClickListener
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * by y on 2017/5/16
 */
abstract class BaseStatusActivity<P : BasePresenterImpl<*, *>> : SwipeBackActivity(), OnStatusClickListener {

    override fun onEmptyClick(view: View) {
        clickNetWork()
    }

    override fun onErrorClick(view: View) {
        clickNetWork()
    }

    override fun onLoadingClick(view: View) {
    }

    override fun onNorMalClick(view: View) {
    }

    override fun onSuccessClick(view: View) {
    }

    protected var mPresenter: P? = null

    private var bundle: Bundle? = null
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("onCreate", javaClass.simpleName)
        setContentView(R.layout.activity_base)
        activity_status_layout.addSuccessView(layoutId)
        activity_status_layout.onStatusClickListener = this
        mPresenter = initPresenter()
        mPresenter?.setNetWorkTag(javaClass.simpleName)
        bundle = intent.extras
        bundle?.let {
            initBundle(it)
        }
        initCreate(savedInstanceState)
        if (supportActionBar != null && !TextUtils.equals(javaClass.simpleName, "MainActivity")) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        if (TextUtils.equals(javaClass.simpleName, "MainActivity")) {
            toolbar.visibility = View.GONE
        }
        swipeBackLayout.setEnableGesture(false) // 暂不开启滑动返回,个人觉得滑动返回和Toolbar的返回键有重复作用.如果开启,要禁止MainActivity右滑返回
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        swipeBackLayout.setEdgeDp(100)
    }

    protected abstract fun initCreate(savedInstanceState: Bundle?)

    protected open fun initBundle(bundle: Bundle) {
    }

    protected abstract fun initPresenter(): P?

    protected open fun clickNetWork() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
        mPresenter = null
        App[this].watch(this)
    }

    fun setStatusViewStatus(status: String) {
        activity_status_layout.setStatus(status)
    }
}

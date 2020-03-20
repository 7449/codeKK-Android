package com.codekk.ui.base

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.status.layout.OnEmptyClick
import com.android.status.layout.OnErrorClick
import com.android.status.layout.addSuccessView
import com.codekk.databinding.ActivityBaseBinding

/**
 * by y on 2017/5/16
 */
abstract class BaseActivity<P : BasePresenterImpl<*, *>>(val layout: Int) : AppCompatActivity() {

    protected val bundle: Bundle? by lazy { intent.extras }

    protected val mPresenter by lazy { initPresenter() }

    protected val baseViewBind: ActivityBaseBinding by lazy { ActivityBaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseViewBind.root)
        if (layout != View.NO_ID) {
            baseViewBind.statusLayout.addSuccessView(layout)
        } else {
            viewBindCreate(savedInstanceState)
        }
        baseViewBind.statusLayout
                .OnEmptyClick { clickNetWork() }
                .OnErrorClick { clickNetWork() }
        bundle?.let { initBundle(it) }
        initCreate(savedInstanceState)
        if (!TextUtils.equals(javaClass.simpleName, "MainActivity")) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected abstract fun initPresenter(): P

    protected abstract fun initCreate(savedInstanceState: Bundle?)

    protected open fun initBundle(bundle: Bundle) {
    }

    protected open fun viewBindCreate(savedInstanceState: Bundle?) {
    }

    protected open fun clickNetWork() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}

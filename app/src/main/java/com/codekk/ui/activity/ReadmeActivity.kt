package com.codekk.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.codekk.Constant
import com.codekk.R
import com.codekk.ext.ReadmeModel
import com.codekk.ext.empty
import com.codekk.ext.error
import com.codekk.ext.snackBar
import com.codekk.mvp.presenter.impl.ReadmePresenterImpl
import com.codekk.mvp.view.ReadmeView
import com.codekk.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_readme.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.browse

/**
 * by y on 2017/5/16
 */
class ReadmeActivity : BaseActivity<ReadmePresenterImpl>(R.layout.activity_readme), ReadmeView {

    companion object {
        const val KEY = "key"
        const val TYPE = "type"
    }

    private lateinit var detail: Array<String>
    private var type: Int = 0

    override fun onDestroy() {
        super.onDestroy()
        if (markdownView != null) {
            markdownView.destroy()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.readme_menu, menu)
        if (type == Constant.TYPE_JOB || type == Constant.TYPE_BLOG || type == Constant.TYPE_RECOMMEND) {
            menu.findItem(R.id.open_browser).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh_url -> {
                if (markdownView.isLoading) {
                    markdownView.reload()
                } else {
                    statusLayout.snackBar(R.string.net_loading)
                }
                true
            }
            R.id.open_browser -> {
                browse(detail[2], true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initBundle(bundle: Bundle) {
        super.initBundle(bundle)
        detail = bundle.getStringArray(KEY) ?: return
        type = bundle.getInt(TYPE)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        refreshLayout.isEnabled = false
        mPresenter?.netWorkRequest(detail[0], type)
        toolbar.title = detail[1]
        setSupportActionBar(toolbar)
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!refreshLayout.isRefreshing) {
            mPresenter?.netWorkRequest(detail[0], type)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && markdownView.canGoBack()) {
                markdownView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initPresenter(): ReadmePresenterImpl? {
        return ReadmePresenterImpl(this)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: ReadmeModel) {
        if (!TextUtils.isEmpty(entity.content)) {
            markdownView.setMarkDownText(entity.content)
        } else {
            statusLayout.empty()
        }
    }

    override fun netWorkError(throwable: Throwable) {
        statusLayout.error()
        statusLayout.snackBar(R.string.net_error)
    }

    override fun loadWebViewUrl() {
        if (markdownView != null) {
            markdownView.loadUrl(detail[2])
        }
    }
}

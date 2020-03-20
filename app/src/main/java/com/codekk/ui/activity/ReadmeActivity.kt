package com.codekk.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.codekk.Constant
import com.codekk.R
import com.codekk.databinding.ActivityReadmeBinding
import com.codekk.ext.ReadmeModel
import com.codekk.ext.empty
import com.codekk.ext.error
import com.codekk.ext.snackBar
import com.codekk.mvp.presenter.impl.ReadmePresenterImpl
import com.codekk.mvp.view.ReadmeView
import com.codekk.ui.base.BaseViewBindActivity
import org.jetbrains.anko.browse

/**
 * by y on 2017/5/16
 */
class ReadmeActivity : BaseViewBindActivity<ActivityReadmeBinding, ReadmePresenterImpl>(), ReadmeView {

    companion object {
        const val KEY = "key"
        const val TYPE = "type"
    }

    private val detail by lazy { bundle?.getStringArray(KEY).orEmpty() }
    private val type by lazy { bundle?.getInt(TYPE) ?: 0 }

    override fun onDestroy() {
        super.onDestroy()
        viewBind.markdownView.destroy()
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
                if (viewBind.markdownView.isLoading) {
                    viewBind.markdownView.reload()
                } else {
                    baseViewBind.statusLayout.snackBar(R.string.net_loading)
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

    override fun initViewBind(): ActivityReadmeBinding {
        return ActivityReadmeBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): ReadmePresenterImpl {
        return ReadmePresenterImpl(this)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        viewBind.refreshLayout.isEnabled = false
        mPresenter.netWorkRequest(detail[0], type)
        baseViewBind.toolbarRoot.toolbar.title = detail[1]
        setSupportActionBar(baseViewBind.toolbarRoot.toolbar)
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!viewBind.refreshLayout.isRefreshing) {
            mPresenter.netWorkRequest(detail[0], type)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && viewBind.markdownView.canGoBack()) {
                viewBind.markdownView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: ReadmeModel) {
        if (!TextUtils.isEmpty(entity.content)) {
            viewBind.markdownView.setMarkDownText(entity.content)
        } else {
            baseViewBind.statusLayout.empty()
        }
    }

    override fun netWorkError(throwable: Throwable) {
        baseViewBind.statusLayout.error()
        baseViewBind.statusLayout.snackBar(R.string.net_error)
    }

    override fun loadWebViewUrl() {
        viewBind.markdownView.loadUrl(detail[2])
    }
}

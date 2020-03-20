package com.codekk.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.codekk.R
import com.codekk.databinding.LayoutFragmentBinding
import com.codekk.ui.base.ViewBindActivity
import com.codekk.ui.fragment.OpListFragment

/**
 * by y on 2017/5/17
 */
class OpSearchActivity : ViewBindActivity<LayoutFragmentBinding>() {

    companion object {
        const val TEXT_KEY = "text"
    }

    private val text by lazy { intent?.extras?.getString(TEXT_KEY, "").orEmpty() }

    override fun initViewBind(): LayoutFragmentBinding {
        return LayoutFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind.toolbarRoot.toolbar.title = text
        setSupportActionBar(viewBind.toolbarRoot.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, OpListFragment.get(text)).commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.codekk.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.codekk.R
import com.codekk.ui.fragment.OpListFragment
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * by y on 2017/5/17
 */
class OpSearchActivity : AppCompatActivity() {
    companion object {
        const val TEXT_KEY = "text"
    }

    private var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment)
        text = intent?.extras?.getString(TEXT_KEY, "") ?: ""
        toolbar.title = text
        setSupportActionBar(toolbar)
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

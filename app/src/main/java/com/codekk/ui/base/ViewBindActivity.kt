package com.codekk.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindActivity<BIND : ViewBinding> : AppCompatActivity() {

    protected val viewBind by lazy { initViewBind() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBind.root)
    }

    protected abstract fun initViewBind(): BIND

}
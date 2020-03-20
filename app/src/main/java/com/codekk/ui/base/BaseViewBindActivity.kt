package com.codekk.ui.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindActivity<BIND : ViewBinding, P : BasePresenterImpl<*, *>> : BaseActivity<P>(View.NO_ID) {

    protected val viewBind by lazy { initViewBind() }

    override fun viewBindCreate(savedInstanceState: Bundle?) {
        baseViewBind.statusLayout.addView(viewBind.root)
    }

    protected abstract fun initViewBind(): BIND

}
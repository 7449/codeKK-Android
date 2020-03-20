package com.codekk.ui.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.android.status.layout.addSuccessView

abstract class BaseViewBindFragment<BIND : ViewBinding, P : BasePresenterImpl<*, *>> : BaseFragment<P>(View.NO_ID) {

    protected val viewBind by lazy { initViewBind() }

    override fun viewBindCreate(savedInstanceState: Bundle?) {
        super.viewBindCreate(savedInstanceState)
        mStatusView.addSuccessView(viewBind.root)
    }

    protected abstract fun initViewBind(): BIND
}
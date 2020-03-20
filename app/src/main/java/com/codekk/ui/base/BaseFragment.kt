package com.codekk.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.status.layout.*
import com.codekk.R
import com.codekk.ext.success
import io.reactivex.network.RxBus
import io.reactivex.network.RxBusCallBack

/**
 * by y on 2017/5/16
 */
abstract class BaseFragment<P : BasePresenterImpl<*, *>>(val layout: Int) : Fragment(), RxBusCallBack<Any> {

    protected lateinit var mStatusView: StatusLayout
    protected val mPresenter by lazy { initPresenter() }
    protected var page: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::mStatusView.isInitialized) {
            mStatusView = StatusLayout(inflater.context)
            mStatusView.addEmptyView(R.layout.layout_status_empty)
            mStatusView.addErrorView(R.layout.layout_status_error)
            if (layout != View.NO_ID) {
                mStatusView.addSuccessView(layout)
            } else {
                viewBindCreate(savedInstanceState)
            }
            mStatusView.success()
            mStatusView
                    .OnEmptyClick { clickNetWork() }
                    .OnErrorClick { clickNetWork() }
        }
        RxBus.instance.register(javaClass.simpleName, this)
        return mStatusView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActivityCreated()
    }

    protected open fun viewBindCreate(savedInstanceState: Bundle?) {
    }

    protected abstract fun initActivityCreated()

    protected abstract fun initPresenter(): P

    protected open fun clickNetWork() {}

    override fun busOfType(): Class<Any> {
        return Any::class.java
    }

    override fun onDestroyView() {
        super.onDestroyView()
        RxBus.instance.unregister(javaClass.simpleName)
        mPresenter.onDestroy()
    }
}

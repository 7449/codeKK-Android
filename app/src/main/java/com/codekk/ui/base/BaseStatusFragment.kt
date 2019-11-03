package com.codekk.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codekk.App
import com.codekk.R
import com.status.layout.StatusLayout
import io.reactivex.network.RxBus
import io.reactivex.network.RxBusCallBack

/**
 * by y on 2017/5/16
 */

abstract class BaseStatusFragment<P : BasePresenterImpl<*, *>> : Fragment(), RxBusCallBack<Any> {

    protected lateinit var mStatusView: StatusLayout
    protected var mPresenter: P? = null
    protected var page: Int = 0

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::mStatusView.isInitialized) {
            mStatusView = StatusLayout(container!!.context)
            mStatusView.addEmptyView(R.layout.layout_status_empty)
            mStatusView.addErrorView(R.layout.layout_status_error)
            mStatusView.addSuccessView(layoutId)
            mStatusView.getView(StatusLayout.EMPTY)?.visibility = View.GONE
            mStatusView.getView(StatusLayout.ERROR)?.visibility = View.GONE
        }
        RxBus.instance.register(javaClass.simpleName, this)
        return mStatusView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter = initPresenter()
        mPresenter?.setNetWorkTag(javaClass.simpleName)
        mStatusView.getView(StatusLayout.EMPTY)?.setOnClickListener { clickNetWork() }
        mStatusView.getView(StatusLayout.ERROR)?.setOnClickListener { clickNetWork() }
        initActivityCreated()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    protected abstract fun initActivityCreated()

    protected abstract fun initPresenter(): P?

    protected open fun clickNetWork() {}

    override fun onBusError(throwable: Throwable) {
    }

    override fun busOfType(): Class<Any> {
        return Any::class.java
    }

    override fun onDestroyView() {
        super.onDestroyView()
        RxBus.instance.unregister(javaClass.simpleName)
        mPresenter?.onDestroy()
        mPresenter = null
        activity?.let {
            App[it].watch(this)
        }
    }

    fun setStatusViewStatus(status: String) {
        mStatusView.setStatus(status)
    }
}

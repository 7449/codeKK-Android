package com.codekk.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekk.App;
import com.codekk.R;
import com.codekk.data.Constant;
import com.common.widget.StatusLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.network.bus.RxBus;
import io.reactivex.network.bus.RxBusCallBack;

/**
 * by y on 2017/5/16
 */

public abstract class BaseStatusFragment<P extends BasePresenterImpl> extends Fragment implements RxBusCallBack<Object> {

    protected StatusLayout mStatusView;
    protected P mPresenter;
    protected int page;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mStatusView == null) {
            mStatusView = new StatusLayout(container.getContext());
            mStatusView.setSuccessView(getLayoutId());
            mStatusView.setEmptyView(R.layout.layout_empty_view);
            mStatusView.setErrorView(R.layout.layout_network_error);
        }
        bind = ButterKnife.bind(this, mStatusView);
        RxBus.getInstance().register(getClass().getSimpleName(), this);
        return mStatusView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setNetWorkTag(getClass().getSimpleName());
            mPresenter.setRootView(mStatusView);
        }
        initActivityCreated();
        mStatusView.getEmptyView().setOnClickListener(v -> clickNetWork());
        mStatusView.getErrorView().setOnClickListener(v -> clickNetWork());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected abstract void initActivityCreated();

    protected abstract P initPresenter();

    protected abstract int getLayoutId();


    protected void clickNetWork() {
    }

    @Override
    public void onBusError(Throwable throwable) {

    }

    @Override
    public Class<Object> busOfType() {
        return Object.class;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unregister(getClass().getSimpleName());
        if (mPresenter != null) {
            mPresenter.onDestroy(Constant.TYPE_NO_FINISH);
            mPresenter = null;
        }
        if (bind != null) {
            bind.unbind();
        }
        App.get(getActivity()).watch(this);
    }
}

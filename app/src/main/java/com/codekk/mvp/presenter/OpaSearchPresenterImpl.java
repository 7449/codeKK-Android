package com.codekk.mvp.presenter;


import android.support.annotation.NonNull;

import com.codekk.mvp.model.OpaSearchModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;
import com.common.widget.StatusLayout;

import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/17
 */

public class OpaSearchPresenterImpl extends BasePresenterImpl<ViewManager.OpaSearchView, OpaSearchModel> implements PresenterManager.OpaSearchPresenter {


    public OpaSearchPresenterImpl(ViewManager.OpaSearchView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String text, int page) {
        netWork(RxNetWork
                .observable(Api.OpaService.class)
                .getOpaSearch(text, page)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(OpaSearchModel data) {
        if (data.getSummaryArray().isEmpty()) {
            view.noMore();
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

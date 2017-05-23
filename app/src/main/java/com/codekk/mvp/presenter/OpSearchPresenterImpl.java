package com.codekk.mvp.presenter;

import android.support.annotation.NonNull;

import com.codekk.mvp.model.OpSearchModel;
import com.codekk.mvp.view.ViewManager;

import com.codekk.ui.base.BasePresenterImpl;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/17
 */

public class OpSearchPresenterImpl extends BasePresenterImpl<ViewManager.OpSearchView, OpSearchModel> implements PresenterManager.OpSearchPresenter {


    public OpSearchPresenterImpl(ViewManager.OpSearchView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String text, int page) {
        netWork(RxNetWork
                .observable(Api.OpService.class)
                .getOpSearch(text, page)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(OpSearchModel data) {
        if (data.getProjectArray().isEmpty()) {
            view.noMore();
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

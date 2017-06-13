package com.codekk.mvp.presenter;

import com.codekk.mvp.model.OpaListModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;
import com.common.widget.StatusLayout;

import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/18
 */

public class OpaListPresenterImpl extends BasePresenterImpl<ViewManager.OpaListView, OpaListModel> implements PresenterManager.OpaListPresenter {


    public OpaListPresenterImpl(ViewManager.OpaListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int page) {
        netWork(RxNetWork
                .observable(Api.OpaService.class)
                .getOpaList(page)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(OpaListModel data) {
        if (data.getSummaryArray().isEmpty()) {
            view.noMore();
            setRootViewState(StatusLayout.EMPTY);
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

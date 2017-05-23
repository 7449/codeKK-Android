package com.codekk.mvp.presenter;

import com.codekk.mvp.model.RecommendListModel;
import com.codekk.mvp.view.ViewManager;

import com.codekk.ui.base.BasePresenterImpl;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/20.
 */

public class RecommendListPresenterImpl extends BasePresenterImpl<ViewManager.RecommendListView, RecommendListModel> implements PresenterManager.RecommendListPresenter {

    public RecommendListPresenterImpl(ViewManager.RecommendListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int page) {
        netWork(RxNetWork
                .observable(Api.RecommendService.class)
                .getRecommendList(page)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(RecommendListModel data) {
        if (data.getRecommendArray().isEmpty()) {
            view.noMore();
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

package com.codekk.mvp.presenter;

import android.support.annotation.NonNull;

import com.codekk.mvp.model.RecommendSearchModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;
import com.common.widget.StatusLayout;

import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/20.
 */

public class RecommendSearchPresenterImpl extends BasePresenterImpl<ViewManager.RecommendSearchView, RecommendSearchModel> implements PresenterManager.RecommendSearchPresenter {
    public RecommendSearchPresenterImpl(ViewManager.RecommendSearchView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String name, int page) {
        netWork(RxNetWork
                .observable(Api.RecommendService.class)
                .getRecommendSearch(name, page)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(RecommendSearchModel data) {
        if (data.getRecommendArray().isEmpty()) {
            view.noMore();
            setRootViewState(StatusLayout.EMPTY);
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

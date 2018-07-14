package com.codekk.mvp.presenter;

import com.codekk.mvp.model.BlogListModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;

import io.reactivex.network.RxNetWork;


/**
 * by y on 2017/5/19
 */

public class BlogListPresenterImpl extends BasePresenterImpl<ViewManager.BlogListView, BlogListModel> implements PresenterManager.BlogListPresenter {
    public BlogListPresenterImpl(ViewManager.BlogListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int page) {
        netWork(RxNetWork
                .observable(Api.BlogService.class)
                .getBlogList(page)
                .map(new NetFunc<>()));
    }


    @Override
    public void onNetWorkSuccess(BlogListModel data) {
        if (data.getSummaryArray().isEmpty()) {
            view.noMore();
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

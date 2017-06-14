package com.codekk.mvp.presenter;

import com.codekk.data.Constant;
import com.codekk.mvp.model.OpListModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;
import com.common.widget.StatusLayout;

import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/16
 */

public class OpListPresenterImpl extends BasePresenterImpl<ViewManager.OpListView, OpListModel>
        implements PresenterManager.OpListPresenter {

    public OpListPresenterImpl(ViewManager.OpListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int page) {
        netWork(RxNetWork
                .observable(Api.OpService.class)
                .getOpList(page, Constant.OP_LIST_TYPE_ARRAY)
                .map(new NetFunc<>()));
    }

    @Override
    public void onNetWorkSuccess(OpListModel data) {
        if (data.getProjectArray().isEmpty()) {
            view.noMore();
        } else {
            super.onNetWorkSuccess(data);
        }
    }
}

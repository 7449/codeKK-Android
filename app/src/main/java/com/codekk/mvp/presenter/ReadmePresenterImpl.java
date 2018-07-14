package com.codekk.mvp.presenter;

import android.support.annotation.NonNull;

import com.codekk.data.Constant;
import com.codekk.mvp.model.ReadmeModel;
import com.codekk.mvp.view.ViewManager;
import com.codekk.net.Api;
import com.codekk.net.NetFunc;
import com.codekk.ui.base.BasePresenterImpl;

import io.reactivex.network.RxNetWork;


/**
 * by y on 2017/5/16
 */

public class ReadmePresenterImpl extends BasePresenterImpl<ViewManager.ReadmeView, ReadmeModel> implements PresenterManager.OpDetailPresenter {

    public ReadmePresenterImpl(ViewManager.ReadmeView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String id, int type) {
        switch (type) {
            case Constant.TYPE_JOB:
                netWork(RxNetWork
                        .observable(Api.JobService.class)
                        .getJobDetail(id)
                        .map(new NetFunc<>()));
                break;
            case Constant.TYPE_OP:
                netWork(RxNetWork
                        .observable(Api.OpService.class)
                        .getOpDetail(id)
                        .map(new NetFunc<>()));
                break;
            case Constant.TYPE_BLOG:
                netWork(RxNetWork
                        .observable(Api.BlogService.class)
                        .getBlogDetail(id)
                        .map(new NetFunc<>()));
                break;
            case Constant.TYPE_OPA:
                netWork(RxNetWork
                        .observable(Api.OpaService.class)
                        .getOpaDetail(id)
                        .map(new NetFunc<>()));
                break;
            default:
                if (view != null) {
                    view.loadWebViewUrl();
                }
                break;
        }
    }
}

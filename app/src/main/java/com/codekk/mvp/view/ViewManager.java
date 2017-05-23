package com.codekk.mvp.view;

import android.support.v7.app.AppCompatActivity;

import com.codekk.mvp.model.BlogListModel;
import com.codekk.mvp.model.JobListModel;
import com.codekk.mvp.model.OpListModel;
import com.codekk.mvp.model.OpSearchModel;
import com.codekk.mvp.model.OpaListModel;
import com.codekk.mvp.model.OpaSearchModel;
import com.codekk.mvp.model.ReadmeModel;
import com.codekk.mvp.model.RecommendListModel;
import com.codekk.mvp.model.RecommendSearchModel;

import com.codekk.ui.base.BaseModel;
import com.codekk.ui.base.BaseView;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {




    /**
     * ListView 继承，实现 noMore方法
     *
     * @param <T>
     */
    interface BaseListView<T> extends BaseView<T> {
        void noMore();
    }

    interface BlogListView extends BaseListView<BlogListModel> {
    }

    interface JobListView extends BaseListView<JobListModel> {
    }

    interface OpaListView extends BaseListView<OpaListModel> {
    }

    interface OpaSearchView extends BaseListView<OpaSearchModel> {
    }

    interface ReadmeView extends BaseView<ReadmeModel> {
    }

    interface OpListView extends BaseListView<OpListModel> {
    }

    interface OpSearchView extends BaseListView<OpSearchModel> {
    }

    interface RecommendListView extends BaseListView<RecommendListModel> {
    }

    interface RecommendSearchView extends BaseListView<RecommendSearchModel> {
    }

    interface MainView extends BaseView<BaseModel> {

        void switchSetting();

        AppCompatActivity getMainActivity();

        void onBack();

        void selectMenuFirst();

    }

}

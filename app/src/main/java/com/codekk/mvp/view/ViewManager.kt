package com.codekk.mvp.view


import androidx.appcompat.app.AppCompatActivity
import com.codekk.mvp.model.*
import com.codekk.ui.base.BaseModel
import com.codekk.ui.base.BaseView

/**
 * by y on 2017/5/23.
 */

interface ViewManager {


    /**
     * ListView 继承，实现 noMore方法
     *
     * @param <T>
    </T> */
    interface BaseListView<T> : BaseView<T> {
        fun noMore()
    }

    interface BlogListView : BaseListView<BlogListModel>

    interface JobListView : BaseListView<JobListModel>

    interface OpaListView : BaseListView<OpaListModel>

    interface OpaSearchView : BaseListView<OpaSearchModel>

    interface ReadmeView : BaseView<ReadmeModel> {
        fun loadWebViewUrl()
    }

    interface OpListView : BaseListView<OpListModel>

    interface OpSearchView : BaseListView<OpSearchModel>

    interface RecommendListView : BaseListView<RecommendListModel>

    interface RecommendSearchView : BaseListView<RecommendSearchModel>

    interface MainView : BaseView<BaseModel<*>> {

        val mainActivity: AppCompatActivity

        fun switchSetting()

        fun onBack()

        fun selectMenuFirst()

        fun switchPact()
    }

}

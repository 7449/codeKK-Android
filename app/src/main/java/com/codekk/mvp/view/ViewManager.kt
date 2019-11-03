package com.codekk.mvp.view

import com.codekk.ext.*
import com.codekk.ui.base.BaseListView
import com.codekk.ui.base.BaseView

interface BlogListView : BaseListView<BlogListModel>

interface JobListView : BaseListView<JobListModel>

interface OpaListView : BaseListView<OpaListModel>

interface OpaSearchView : BaseListView<OpaListModel>

interface ReadmeView : BaseView<ReadmeModel> {
    fun loadWebViewUrl()
}

interface OpListView : BaseListView<OpListModel>

interface OpSearchView : BaseListView<OpListModel>

interface RecommendListView : BaseListView<RecommendListModel>

interface RecommendSearchView : BaseListView<RecommendListModel>

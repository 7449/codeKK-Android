package com.codekk.mvp.presenter

interface BlogListPresenter {
    fun netWorkRequest(page: Int)
}

interface JobListPresenter {
    fun netWorkRequest(page: Int)
}

interface RecommendPresenter {
    fun netWorkRequestList(page: Int)
    fun netWorkRequestSearch(name: String, page: Int)
}

interface OpaPresenter {
    fun netWorkRequestList(page: Int)
    fun netWorkRequestSearch(text: String, page: Int)
}

interface OpPresenter {
    fun netWorkRequestList(page: Int)
    fun netWorkRequestSearch(text: String, page: Int)
}

interface ReadmePresenter {
    fun netWorkRequest(id: String, type: Int)
}
package com.codekk.mvp.model

/**
 * by y on 2017/5/19
 */

class BlogListModel {

    lateinit var summaryArray: List<SummaryArrayBean>

    class SummaryArrayBean {
        var _id: String = ""
        var status: Int = 0
        var path: String = ""
        var title: String = ""
        var summary: String = ""
        var authorName: String = ""
        var authorUrl: String = ""
        var type: String = ""
        var tags: String = ""
        var createTime: String = ""
        var updateTime: String = ""
        var codeKKUrl: String = ""
        var fullTitle: String = ""
        var tagList: List<String>? = null
    }
}

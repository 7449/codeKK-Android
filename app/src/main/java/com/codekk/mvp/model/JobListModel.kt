package com.codekk.mvp.model

/**
 * by y on 2017/5/18.
 */

class JobListModel {

    lateinit var summaryArray: List<SummaryArrayBean>

    class SummaryArrayBean {
        var _id: String = ""
        var status: Int = 0
        var codeLang: String = ""
        var source: String = ""
        var title: String = ""
        var summary: String = ""
        var authorUrl: String = ""
        var authorName: String = ""
        var authorCity: String = ""
        var type: String = ""
        var createTime: String = ""
        var expiredTime: String = ""
        var codeKKUrl: String = ""
        var fullTitle: String = ""
    }
}

package com.codekk.mvp.model

/**
 * by y on 2017/5/18.
 */

class OpaSearchModel {


    lateinit var summaryArray: List<SummaryArrayBean>

    class SummaryArrayBean {
        var _id: String = ""
        var status: Int = 0
        var path: String = ""
        var type: String = ""
        var summary: String = ""
        var phase: String = ""
        var tags: String = ""
        var createTime: String = ""
        var updateTime: String = ""
        var codeLang: String = ""
        var source: String = ""
        var title: String = ""
        var projectUrl: String = ""
        var projectName: String = ""
        var demoUrl: String = ""
        var authorUrl: String = ""
        var authorName: String = ""
        var codeKKUrl: String = ""
        var fullTitle: String = ""
        var tagList: List<String>? = null
    }
}

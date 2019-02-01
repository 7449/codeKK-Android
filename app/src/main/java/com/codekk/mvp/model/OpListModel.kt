package com.codekk.mvp.model

/**
 * by y on 2017/5/16
 */

class OpListModel {

    lateinit var projectArray: List<ProjectArrayBean>

    class ProjectArrayBean {

        var projectName: String = ""
        var createTime: String = ""
        var updateTime: String = ""
        var expiredTimes: Int = 0
        var usedTimes: Int = 0
        var voteUp: Int = 0
        var recommend: Boolean = false
        var hide: Boolean = false
        var projectUrl: String = ""
        var demoUrl: String = ""
        var committer: String = ""
        var source: String = ""
        var lang: String = ""
        var authorName: String = ""
        var authorUrl: String = ""
        var codeKKUrl: String = ""
        var _id: String = ""
        var desc: String = ""
        var tags: List<TagsBean>? = null

        class TagsBean {
            var createTime: String = ""
            var name: String = ""
            var userName: String = ""
            var type: String = ""
        }
    }
}

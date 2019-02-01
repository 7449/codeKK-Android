package com.codekk.mvp.model

/**
 * by y on 2017/5/17
 */

class OpSearchModel {

    var isFullSearch: Boolean = false
    var totalCount: Int = 0
    var pageSize: Int = 0
    lateinit var projectArray: List<ProjectArrayBean>

    class ProjectArrayBean {
        var lang: String = ""
        var committer: String = ""
        var updateTime: String = ""
        var isHide: Boolean = false
        var authorUrl: String = ""
        var projectName: String = ""
        var expiredTimes: Int = 0
        var authorName: String = ""
        var usedTimes: Int = 0
        var source: String = ""
        var projectUrl: String = ""
        var recommend: Boolean = false
        var desc: String = ""
        var createTime: String = ""
        var voteUp: Int = 0
        var _id: String = ""
        var codeKKUrl: String = ""
        var tags: List<TagsBean>? = null

        class TagsBean {
            var userName: String = ""
            var contentId: String = ""
            var type: String = ""
            var createTime: String = ""
            var name: String = ""
        }
    }
}

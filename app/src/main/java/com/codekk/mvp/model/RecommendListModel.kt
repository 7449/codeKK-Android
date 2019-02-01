package com.codekk.mvp.model

/**
 * by y on 2017/5/20.
 */

class RecommendListModel {

    lateinit var recommendArray: List<RecommendArrayBean>

    class RecommendArrayBean {

        var toCodeKKWx: Boolean = false
        var isDelete: Boolean = false
        var createTime: String = ""
        var url: String = ""
        var title: String = ""
        var desc: String = ""
        var type: String = ""
        var userName: String = ""
        var _id: String = ""
        var codeKKUrl: String = ""
        var isFavorite: Boolean = false
        var encodeUrl: String = ""
        var articleTags: List<ArticleTagsBean>? = null
        var contentTypes: List<String>? = null

        class ArticleTagsBean {
            var createTime: String = ""
            var name: String = ""
            var userName: String = ""
            var type: String = ""
        }
    }
}

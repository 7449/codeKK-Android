package com.codekk.ext

import com.google.gson.annotations.SerializedName

data class BaseModel<T>(var message: String = "", var code: Int = 0, var data: T)

data class OpListModel(@SerializedName("projectArray") var opList: List<OpListBean>)

data class OpListBean(@SerializedName("_id") var id: String,
                      var desc: String,
                      var authorUrl: String,
                      var projectName: String,
                      var projectUrl: String,
                      var authorName: String,
                      var tags: List<OpTagsBean>?)

data class OpTagsBean(var name: String)

data class OpaListModel(@SerializedName("summaryArray") var opaList: List<OpaListBean>)

data class OpaListBean(@SerializedName("_id") var id: String,
                       var summary: String,
                       var title: String,
                       var projectUrl: String,
                       var projectName: String,
                       var authorName: String,
                       var tagList: List<String>?)

data class JobListModel(@SerializedName("summaryArray") var jobList: List<JobListBean>)

data class JobListBean(@SerializedName("_id") var id: String,
                       var summary: String,
                       var projectUrl: String,
                       var projectName: String,
                       var authorName: String,
                       var authorCity: String,
                       var expiredTime: String)

data class BlogListModel(@SerializedName("summaryArray") var blogList: List<BlogListBean>)

data class BlogListBean(@SerializedName("_id") var id: String,
                        var title: String,
                        var summary: String,
                        var projectUrl: String,
                        var projectName: String,
                        var authorName: String,
                        var tagList: List<String>?)

data class RecommendListModel(@SerializedName("recommendArray") var recommendList: List<RecommendListBean>)

data class RecommendListBean(var url: String,
                             var title: String,
                             var desc: String)

data class ReadmeModel(var content: String)
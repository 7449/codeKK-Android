package com.codekk.ext

import com.google.gson.annotations.SerializedName

data class BaseModel<T>(val message: String = "", val code: Int = 0, val data: T)

data class OpListModel(@SerializedName("projectArray") val opList: List<OpListBean>)

data class OpListBean(@SerializedName("_id") val id: String,
                      val desc: String,
                      val authorUrl: String,
                      val projectName: String,
                      val projectUrl: String,
                      val authorName: String,
                      val tags: List<OpTagsBean>?)

data class OpTagsBean(val name: String)

data class OpaListModel(@SerializedName("summaryArray") val opaList: List<OpaListBean>)

data class OpaListBean(@SerializedName("_id") val id: String,
                       val summary: String,
                       val title: String,
                       val projectUrl: String,
                       val projectName: String,
                       val authorName: String,
                       val tagList: List<String>?)

data class JobListModel(@SerializedName("summaryArray") val jobList: List<JobListBean>)

data class JobListBean(@SerializedName("_id") val id: String,
                       val summary: String,
                       val projectUrl: String,
                       val projectName: String,
                       val authorName: String,
                       val authorCity: String,
                       val expiredTime: String)

data class BlogListModel(@SerializedName("summaryArray") val blogList: List<BlogListBean>)

data class BlogListBean(@SerializedName("_id") val id: String,
                        val title: String,
                        val summary: String,
                        val projectUrl: String,
                        val projectName: String,
                        val authorName: String,
                        val tagList: List<String>?)

data class RecommendListModel(@SerializedName("recommendArray") val recommendList: List<RecommendListBean>)

data class RecommendListBean(val url: String,
                             val title: String,
                             val desc: String)

data class ReadmeModel(val content: String)
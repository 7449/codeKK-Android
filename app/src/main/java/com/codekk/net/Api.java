package com.codekk.net;


import com.codekk.mvp.model.BlogListModel;
import com.codekk.mvp.model.JobListModel;
import com.codekk.mvp.model.OpListModel;
import com.codekk.mvp.model.OpSearchModel;
import com.codekk.mvp.model.OpaListModel;
import com.codekk.mvp.model.OpaSearchModel;
import com.codekk.mvp.model.ReadmeModel;
import com.codekk.mvp.model.RecommendListModel;
import com.codekk.mvp.model.RecommendSearchModel;
import com.codekk.ui.base.BaseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * by y on 2017/5/16
 */

public class Api {
    private Api() {
    }


    public static final String BASE_API = "http://api.codekk.com/";

    private static final String OP_LIST_URL = "op/page/"; //获取开源项目
    private static final String OP_DETAIL_URL = "op/detail/"; //获取单个开源项目 ReadMe
    private static final String OP_SEARCH_URL = "op/search"; //搜索开源项目

    private static final String OPA_LIST_URL = "opa/page/"; //获取源码解析文章列表
    private static final String OPA_DETAIL_URL = "opa/detail/"; //获取单个源码解析文章详情
    private static final String OPA_SEARCH_URL = "opa/user/";

    private static final String JOB_LIST_URL = "job/page/"; //获取职位内推文章列表
    private static final String JOB_DETAIL_URL = "job/detail/"; //获取单个职位内推文章详情

    private static final String BLOG_LIST_URL = "blog/page/"; //获取博客文章列表
    private static final String BLOG_DETAIL_URL = "blog/detail/"; //获取单个博客文章详情

    private static final String RECOMMEND_LIST_URL = "recommend/page/"; //获取今日推荐列表
    private static final String RECOMMEND_SEARCH_URL = "recommend/user/"; //根据推荐者查询推荐列表


    public interface OpService {
        @GET(Api.OP_LIST_URL + "{page}")
        Observable<BaseModel<OpListModel>> getOpList(@Path("page") int page, @Query("type") String type);

        @GET(Api.OP_DETAIL_URL + "{id}" + "/readme")
        Observable<BaseModel<ReadmeModel>> getOpDetail(@Path("id") String id);

        @GET(Api.OP_SEARCH_URL)
        Observable<BaseModel<OpSearchModel>> getOpSearch(@Query("text") String text, @Query("page") int page);
    }

    public interface OpaService {
        @GET(Api.OPA_LIST_URL + "{page}")
        Observable<BaseModel<OpaListModel>> getOpaList(@Path("page") int page);

        @GET(Api.OPA_DETAIL_URL + "{id}")
        Observable<BaseModel<ReadmeModel>> getOpaDetail(@Path("id") String id);

        @GET(Api.OPA_SEARCH_URL + "{name}/page/{page}")
        Observable<BaseModel<OpaSearchModel>> getOpaSearch(@Path("name") String name, @Path("page") int page);
    }

    public interface JobService {
        @GET(Api.JOB_LIST_URL + "{page}")
        Observable<BaseModel<JobListModel>> getJobList(@Path("page") int page);

        @GET(Api.JOB_DETAIL_URL + "{id}")
        Observable<BaseModel<ReadmeModel>> getJobDetail(@Path("id") String id);
    }

    public interface BlogService {
        @GET(Api.BLOG_LIST_URL + "{page}")
        Observable<BaseModel<BlogListModel>> getBlogList(@Path("page") int page);

        @GET(Api.BLOG_DETAIL_URL + "{id}")
        Observable<BaseModel<ReadmeModel>> getBlogDetail(@Path("id") String id);
    }

    public interface RecommendService {
        @GET(Api.RECOMMEND_LIST_URL + "{page}")
        Observable<BaseModel<RecommendListModel>> getRecommendList(@Path("page") int page);

        @GET(Api.RECOMMEND_SEARCH_URL + "{user}/page/{page}")
        Observable<BaseModel<RecommendSearchModel>> getRecommendSearch(@Path("user") String name, @Path("page") int page);
    }
}

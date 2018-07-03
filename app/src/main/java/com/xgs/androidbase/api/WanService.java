package com.xgs.androidbase.api;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by W.J on 2018/6/26.
 */

public interface WanService {
    public final String HOST = "http://www.wanandroid.com/";

    @GET("project/tree/json")
    Observable<BaseWanBean<ProjectTreeBean>> getTree();

    @GET("project/list/{pageNo}/json")
    Observable<ProjectBaseBean> getProject(@Path("pageNo") int pageNo, @Query("cid") Long cid);
}

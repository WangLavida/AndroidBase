package com.xgs.androidbase.api;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by W.J on 2018/6/26.
 */

public interface WanService {
    public final String HOST = "http://www.wanandroid.com/";

    @GET("project/tree/json")
    Observable<BaseWanBean<ProjectTreeBean>> getTree();
}

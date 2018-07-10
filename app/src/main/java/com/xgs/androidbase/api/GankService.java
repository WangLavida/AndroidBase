package com.xgs.androidbase.api;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by W.J on 2018/7/10.
 */

public interface GankService {
    public final String HOST = "http://gank.io/api/data/";
    @GET("福利/{num}/{pageNo}")
    Observable<GankBaseBean> getGank(@Path("num") int num,@Path("pageNo") int pageNo);
}

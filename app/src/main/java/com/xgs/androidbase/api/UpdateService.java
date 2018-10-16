package com.xgs.androidbase.api;

import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.bean.UpdateBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by W.J on 2018/10/16.
 */

public interface UpdateService {
    public final String HOST = "https://raw.githubusercontent.com/WangLavida/AndroidBase/master/";

    @GET("update.json")
    Observable<UpdateBean> update();

    /**
     * @param url   文件下载的url
     * @return Observable
     * @Streaming 这个注解必须添加，否则文件全部写入内存，文件过大会造成内存溢出
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}

package com.xgs.androidbase.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.xgs.androidbase.MainApplication;
import com.xgs.androidbase.util.NetUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by W.J on 2018/6/26.
 */

public class Api {
    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 30000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 30000;
    public Retrofit retrofit;
    private static WanService wanService;
    private static GankService gankService;
//    1. noCache 不使用缓存，全部走网络
//
//    2. noStore 不使用缓存，也不存储缓存
//
//    3. onlyIfCached 只使用缓存
//
//    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合
//
//    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言
//
//    6. minFresh 设置有效时间，依旧如上
//
//    7. FORCE_NETWORK 只走网络
//
//    8. FORCE_CACHE 只走缓存
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    //日志打印
    private static final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    //缓存
    private File cacheFile = new File(MainApplication.getAppContext().getCacheDir(), "cache");
    private Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
    //增加头部信息
    private Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(build);
        }
    };
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .addInterceptor(sLoggingInterceptor)
            .addNetworkInterceptor(sRewriteCacheControlInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(logInterceptor)
            .cache(cache)
            .build();

    public Api(String host) {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(host)
                .build();
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        return new Api(url).retrofit.create(clazz);
    }

    public static WanService createWan() {
        if (wanService == null) {
            wanService = new Api(WanService.HOST).retrofit.create(WanService.class);
        }
        return wanService;
    }
    public static GankService createGank() {
        if (gankService == null) {
            gankService = new Api(GankService.HOST).retrofit.create(GankService.class);
        }
        return gankService;
    }
    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
//                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.i(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(MainApplication.getAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(MainApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}

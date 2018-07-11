package com.xgs.androidbase;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.util.SharedPreferencesUtil;

import org.w3c.dom.ProcessingInstruction;


/**
 * Created by W.J on 2018/6/22.
 */

public class MainApplication extends Application {
    private static MainApplication mainApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        initDayNight();
        Utils.init(this);
        initLog();
    }
    private void initDayNight(){
        boolean isNightMode = (boolean) SharedPreferencesUtil.getData(mainApplication, Constant.DAY_NIGHT,false);
        if (isNightMode) {
//            直接指定夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
//            直接指定日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    public static Context getAppContext() {
        return mainApplication;
    }
    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
//                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//                .tag("SGX")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
//        FormatStrategy formatStrategy1 = CsvFormatStrategy.newBuilder()
//                .tag("disk")
//                .build();
//        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy1));
    }
}

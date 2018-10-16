package com.xgs.androidbase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.tinker.Log.MyLogImp;
import com.xgs.androidbase.tinker.reporter.SampleLoadReporter;
import com.xgs.androidbase.tinker.reporter.SamplePatchListener;
import com.xgs.androidbase.tinker.reporter.SamplePatchReporter;
import com.xgs.androidbase.tinker.service.SampleResultService;
import com.xgs.androidbase.tinker.util.TinkerManager;
import com.xgs.androidbase.util.SharedPreferencesUtil;

import org.w3c.dom.ProcessingInstruction;


/**
 * Created by W.J on 2018/6/22.
 */

@DefaultLifeCycle(application = ".MyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",
        loadVerifyFlag = false)
public class MainApplication extends ApplicationLike {
    private static Application mainApplication;
    private Context mContext;

    public MainApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mainApplication = getApplication();
        initDayNight();
        Utils.init(mainApplication);
        initLog();
        // 分包需要的东西
        MultiDex.install(base);
        // LoadReporter类定义了Tinker在加载补丁时的一些回调
        LoadReporter loadReporter = new DefaultLoadReporter(getApplication());
        // PatchReporter类定义了Tinker在修复或者升级补丁时的一些回调
        PatchReporter patchReporter = new DefaultPatchReporter(getApplication());
        // PatchListener类是用来过滤Tinker收到的补丁包的修复、升级请求，也就是决定我们是不是真的要唤起:patch进程去尝试补丁合成。
        PatchListener patchListener = new DefaultPatchListener(getApplication());
        // UpgradePatch类是用来升级当前补丁包的处理类，一般来说你也不需要复写它。
        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(this, loadReporter, patchReporter, patchListener, DefaultTinkerResultService.class, upgradePatchProcessor);
    }

    private void initDayNight() {
        boolean isNightMode = (boolean) SharedPreferencesUtil.getData(mainApplication, Constant.DAY_NIGHT, false);
        if (isNightMode) {
//            直接指定夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
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

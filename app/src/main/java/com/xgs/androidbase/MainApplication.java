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
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
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

/**
 * 使用DefaultLifeCycle注解生成Application（这种方式是Tinker官方推荐的）
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = ".MyApplication",// application类名。只能用字符串，这个MyApplication文件是不存在的，但可以在AndroidManifest.xml的application标签上使用（name）
        flags = ShareConstants.TINKER_ENABLE_ALL,// tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",//loaderClassName, 我们这里使用默认即可!（可不写）
        loadVerifyFlag = false)//tinkerLoadVerifyFlag
public class MainApplication extends DefaultApplicationLike {
    private static Application mainApplication;
    private Context mContext;
    private Tinker mTinker;
    public MainApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    // 固定写法
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mainApplication = getApplication();
        mContext = getApplication();
        initTinker(base);
        initDayNight();
        Utils.init(mainApplication);
        initLog();
        // 可以将之前自定义的Application中onCreate()方法所执行的操作搬到这里...
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

    private void initTinker(Context base) {
        // tinker需要你开启MultiDex
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);
        // 设置全局异常捕获
        TinkerManager.initFastCrashProtect();
        //开启升级重试功能（在安装Tinker之前设置）
        TinkerManager.setUpgradeRetryEnable(true);
        //设置Tinker日志输出类
        TinkerInstaller.setLogIml(new MyLogImp());
        //安装Tinker(在加载完multiDex之后，否则你需要将com.tencent.tinker.**手动放到main dex中)
        TinkerManager.installTinker(this);
        //or you can just use DefaultLoadReporter
        LoadReporter loadReporter = new SampleLoadReporter(base);
//or you can just use DefaultPatchReporter
        PatchReporter patchReporter = new SamplePatchReporter(base);
//or you can just use DefaultPatchListener
        PatchListener patchListener = new SamplePatchListener(base);
//you can set your own upgrade patch if you need
        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(MainApplication.this,
                loadReporter, patchReporter, patchListener,
                SampleResultService.class, upgradePatchProcessor);
        mTinker = Tinker.with(getApplication());
    }
}

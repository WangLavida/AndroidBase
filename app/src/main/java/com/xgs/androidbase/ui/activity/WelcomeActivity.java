package com.xgs.androidbase.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.xgs.androidbase.BuildConfig;
import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.UpdateBean;
import com.xgs.androidbase.common.PorjectListConvert;
import com.xgs.androidbase.impl.DownloadListener;
import com.xgs.androidbase.ui.contract.WelcomeContract;
import com.xgs.androidbase.ui.model.WelcomeModel;
import com.xgs.androidbase.ui.presenter.WelcomePresenter;
import com.xgs.androidbase.util.DownloadUtil;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ToastUtil;
import com.xgs.androidbase.view.NumProDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity<WelcomePresenter, WelcomeModel> implements WelcomeContract.View, DownloadListener {
    private BaseWanBean<ProjectTreeBean> baseWanBean;
    private NumProDialog numProDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        interval();
        ToastUtil.showShort(BuildConfig.FLAVOR);
        initPermission();
    }

    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.USE_FINGERPRINT).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    mPresenter.getProjectTree();
                } else {
                    ToastUtil.showShort("无法使用");
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 倒计时
     */
    private void interval() {
        Observable.interval(1, TimeUnit.SECONDS).take(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                startMain();
            }
        });
    }

    private void startMain() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void saveProjectTree(BaseWanBean<ProjectTreeBean> baseWanBean) {
        this.baseWanBean = baseWanBean;
        mPresenter.getDbProject();
    }

    @Override
    public void getDbProject(List<ProjectTreeBean> projectTreeBeanList) {
        if (PorjectListConvert.convertList(baseWanBean, projectTreeBeanList).size() != 0) {
            mPresenter.saveProjectTree(PorjectListConvert.convertList(baseWanBean, projectTreeBeanList));
        }
        mPresenter.update();
//        startMain();
    }

    @Override
    public void update(UpdateBean updateBean) {
        LogUtil.i(updateBean.getVersion());
        boolean isLoaded = Tinker.with(mContext).isTinkerLoaded();
        String oldTinkerId;
        String newTinkerId = "";
        if (isLoaded) {
            //获得基准包的tinkerId
            oldTinkerId = Tinker.with(mContext).getTinkerLoadResultIfPresent().patchInfo.oldVersion;
            //获得补丁包的tinkerId
            newTinkerId = Tinker.with(mContext).getTinkerLoadResultIfPresent().patchInfo.newVersion;
            LogUtil.i("old:" + oldTinkerId + ";new:" + newTinkerId);
        }
        if (!isLoaded || (isLoaded && Integer.parseInt(newTinkerId) < updateBean.getVersion())) {
            DownloadUtil downloadUtil = new DownloadUtil();
            downloadUtil.downloadFile(updateBean.getUrl(), this);
        } else {
            startMain();
        }

    }

    @Override
    public void startLoad() {

    }

    @Override
    public void onError() {

    }


    @Override
    public void onDownloadStart() {
        LogUtil.i("onDownloadStart");
        numProDialog = new NumProDialog(this);
        numProDialog.show();
    }

    @Override
    public void onDownloadProgress(int currentLength) {
        LogUtil.i("onDownloadProgress" + currentLength);
        numProDialog.setProgress(currentLength);
    }

    @Override
    public void onDownloadFinish(String localPath) {
        LogUtil.i("onDownloadFinish" + localPath);
        numProDialog.dismiss();
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), localPath);
        startMain();
    }

    @Override
    public void onDownloadFailure(String erroInfo) {
        LogUtil.i("onDownloadFailure");
    }
}

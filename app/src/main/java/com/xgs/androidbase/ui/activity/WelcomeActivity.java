package com.xgs.androidbase.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.common.PorjectListConvert;
import com.xgs.androidbase.ui.contract.WelcomeContract;
import com.xgs.androidbase.ui.model.WelcomeModel;
import com.xgs.androidbase.ui.presenter.WelcomePresenter;
import com.xgs.androidbase.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity<WelcomePresenter, WelcomeModel> implements WelcomeContract.View {
   private BaseWanBean<ProjectTreeBean> baseWanBean;
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
        mPresenter.getProjectTree();
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
        LogUtil.i("数据类型数量" + baseWanBean.getData().size());
        this.baseWanBean = baseWanBean;
        mPresenter.getDbProject();
    }

    @Override
    public void getDbProject(List<ProjectTreeBean> projectTreeBeanList) {
        LogUtil.i("数据类型数量1" + projectTreeBeanList.size());
        mPresenter.saveProjectTree(PorjectListConvert.convertList(baseWanBean,projectTreeBeanList));
        startMain();
    }
}

package com.xgs.androidbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xgs.androidbase.R;
import com.xgs.androidbase.api.Api;
import com.xgs.androidbase.api.WanService;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.common.RxSchedulers;
import com.xgs.androidbase.ui.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        interval();
        Api.createWan().getTree().compose(RxSchedulers.<BaseWanBean<ProjectTreeBean>>rxSchedulerHelper()).subscribe(new Consumer<BaseWanBean<ProjectTreeBean>>() {
            @Override
            public void accept(BaseWanBean<ProjectTreeBean> baseWanBean) throws Exception {

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
}

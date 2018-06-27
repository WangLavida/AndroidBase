package com.xgs.androidbase.common.rx;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by W.J on 2018/6/27.
 */

public abstract class RxObserver<T> implements Observer<T> {
    private Context mContext;
    private RxManager mRxManager;

    public RxObserver(Context mContext, RxManager mRxManager) {
        this.mContext = mContext;
        this.mRxManager = mRxManager;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mRxManager.register(d);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}

package com.xgs.androidbase.common.rx;

import android.accounts.NetworkErrorException;
import android.content.Context;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by W.J on 2018/6/27.
 */

public abstract class RxObserver<T> implements Observer<T> {
    private Context mContext;
    private RxManager mRxManager;

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable e,boolean isNetWorkError);

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
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFail(e, true);
            } else {
                onFail(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }
}

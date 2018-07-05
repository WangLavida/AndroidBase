package com.xgs.androidbase.base;

import android.content.Context;

import com.xgs.androidbase.common.rx.RxManager;

/**
 * Created by W.J on 2018/6/21.
 */

public abstract class BasePresenter<T, E> {
    public Context mContext;
    public T mView;
    public E mModel;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {
    }

    ;

    /**
     * 解绑IModel和IView
     */
    public void detachMV() {
        mRxManager.unSubscribe();
        mView = null;
        mModel = null;
    }
}

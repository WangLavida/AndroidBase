package com.xgs.androidbase.base;

import android.content.Context;

/**
 * Created by W.J on 2018/6/21.
 */

public abstract class BasePresenter<T, E> {
    public Context mContext;
    public T mView;
    public E mModel;

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
    }
}

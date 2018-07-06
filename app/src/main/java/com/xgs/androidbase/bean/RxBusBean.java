package com.xgs.androidbase.bean;

/**
 * Created by W.J on 2018/7/6.
 */

public class RxBusBean {
    public String TAG;
    public Object content;

    public RxBusBean(String TAG, Object content) {
        this.TAG = TAG;
        this.content = content;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

package com.xgs.androidbase.bean;

import java.util.List;

/**
 * Created by W.J on 2018/7/10.
 */

public class GankBaseBean {
    public boolean error;
    public List<GankBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankBean> getResults() {
        return results;
    }

    public void setResults(List<GankBean> results) {
        this.results = results;
    }
}

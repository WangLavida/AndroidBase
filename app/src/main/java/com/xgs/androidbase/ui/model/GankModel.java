package com.xgs.androidbase.ui.model;

import com.xgs.androidbase.api.Api;
import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.common.rx.RxSchedulers;
import com.xgs.androidbase.ui.contract.GankContract;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/7/10.
 */

public class GankModel implements GankContract.Model {
    @Override
    public Observable<GankBaseBean> getData(int num, int pageNo) {
        return Api.createGank().getGank(num,pageNo).compose(RxSchedulers.<GankBaseBean>rxSchedulerHelper());
    }
}

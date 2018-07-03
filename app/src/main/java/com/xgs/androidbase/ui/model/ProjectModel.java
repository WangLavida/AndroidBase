package com.xgs.androidbase.ui.model;

import com.xgs.androidbase.api.Api;
import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.common.rx.RxSchedulers;
import com.xgs.androidbase.ui.contract.ProjectContract;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/7/3.
 */

public class ProjectModel implements ProjectContract.Model {
    @Override
    public Observable<ProjectBaseBean> getProjectList(int pageNo,Long cid) {
        return Api.createWan().getProject(pageNo,cid).compose(RxSchedulers.<ProjectBaseBean>rxSchedulerHelper());
    }
}

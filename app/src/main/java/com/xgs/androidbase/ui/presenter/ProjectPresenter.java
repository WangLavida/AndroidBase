package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.ProjectContract;
import com.xgs.androidbase.util.LogUtil;

import io.reactivex.functions.Consumer;

/**
 * Created by W.J on 2018/7/3.
 */

public class ProjectPresenter extends ProjectContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        RxBus.getInstance().toObservable(String.class).subscribe(new RxObserver<String>(mContext,mRxManager) {
            @Override
            public void onSuccess(String s) {
                if (s.equals(Constant.LIST_TO_TOP)) {
                    mView.listToTop();
                }
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void getProjectList(int pageNo, Long cid) {
        mModel.getProjectList(pageNo, cid).subscribe(new RxObserver<ProjectBaseBean>(mContext, mRxManager) {
            @Override
            public void onSuccess(ProjectBaseBean projectBaseBean) {
                mView.returnPojectList(projectBaseBean);
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }
}

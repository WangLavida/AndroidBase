package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.ProjectContract;

/**
 * Created by W.J on 2018/7/3.
 */

public class ProjectPresenter extends ProjectContract.Presenter {
    @Override
    public void getProjectList(int pageNo,Long cid) {
        mModel.getProjectList(pageNo,cid).subscribe(new RxObserver<ProjectBaseBean>(mContext,mRxManager) {
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

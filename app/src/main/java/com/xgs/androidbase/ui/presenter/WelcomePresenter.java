package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.UpdateBean;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.WelcomeContract;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by W.J on 2018/6/27.
 */

public class WelcomePresenter extends WelcomeContract.Presenter {
    @Override
    public void getProjectTree() {
        mModel.getProjectTree().subscribe(new RxObserver<BaseWanBean<ProjectTreeBean>>(mContext, mRxManager) {
            @Override
            public void onSuccess(BaseWanBean<ProjectTreeBean> projectTreeBeanBaseWanBean) {
                mView.saveProjectTree(projectTreeBeanBaseWanBean);
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void getDbProject() {
        mModel.getDbProject(mContext).subscribe(new RxObserver<List<ProjectTreeBean>>(mContext, mRxManager) {
            @Override
            public void onSuccess(List<ProjectTreeBean> projectTreeBeanList) {
                mView.getDbProject(projectTreeBeanList);
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void update() {
        mModel.update(mContext).subscribe(new RxObserver<UpdateBean>(mContext, mRxManager) {
            @Override
            public void onSuccess(UpdateBean updateBean) {
                mView.update(updateBean);
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void saveProjectTree(List<ProjectTreeBean> projectTreeBeanList) {
        mModel.saveProjectTree(mContext, projectTreeBeanList).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }
}

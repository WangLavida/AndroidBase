package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.WelcomeContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by W.J on 2018/6/27.
 */

public class WelcomePresenter extends WelcomeContract.Presenter {
    @Override
    public void getProjectTree() {
        mModel.getProjectTree().subscribe(new RxObserver<BaseWanBean<ProjectTreeBean>>(mContext, mRxManager) {
            @Override
            public void onNext(BaseWanBean<ProjectTreeBean> projectTreeBeanBaseWanBean) {
                super.onNext(projectTreeBeanBaseWanBean);
                mView.saveProjectTree(projectTreeBeanBaseWanBean);
            }
        });
    }
}

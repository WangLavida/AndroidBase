package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.RxBusBean;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.WanContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by W.J on 2018/7/3.
 */

public class WanPresenter extends WanContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        RxBus.getInstance().toObservable(RxBusBean.class).subscribe(new RxObserver<RxBusBean>(mContext,mRxManager) {
            @Override
            public void onSuccess(RxBusBean rxBusBean) {
                if (rxBusBean.getTAG().equals(Constant.ADD_TREE)) {
                   mView.addTree((ProjectTreeBean) rxBusBean.getContent());
                } else if (rxBusBean.getTAG().equals(Constant.REMOVE_TREE)) {
                    mView.removeTree((ProjectTreeBean) rxBusBean.getContent());
                }
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void getFollowProject() {
        mModel.getDbProject(mContext).subscribe(new Consumer<List<ProjectTreeBean>>() {
            @Override
            public void accept(List<ProjectTreeBean> projectTreeBeanList) throws Exception {
                List<ProjectTreeBean> list = new ArrayList<ProjectTreeBean>();
                for (ProjectTreeBean projectTreeBean : projectTreeBeanList) {
                    if (projectTreeBean.isFollow()) {
                        list.add(projectTreeBean);
                    }
                }
                mView.returnFollowProject(list);
            }
        });
    }
}

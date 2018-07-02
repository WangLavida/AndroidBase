package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.FollowManageContract;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by W.J on 2018/6/28.
 */

public class FollowManagePresenter extends FollowManageContract.Presenter {
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
    public void cleanProject() {
        mModel.cleanProject(mContext).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                   mView.updateProject();
            }
        });
    }

    @Override
    public void saveProjectTree(List<ProjectTreeBean> projectTreeBeanList) {
      mModel.saveProjectTree(mContext,projectTreeBeanList).subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {

          }
      });
    }
}

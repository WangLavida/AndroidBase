package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.ui.contract.WanContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by W.J on 2018/7/3.
 */

public class WanPresenter extends WanContract.Presenter {
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

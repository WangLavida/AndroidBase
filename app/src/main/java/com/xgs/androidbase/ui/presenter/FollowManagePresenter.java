package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.ui.contract.FollowManageContract;

import java.util.List;

/**
 * Created by W.J on 2018/6/28.
 */

public class FollowManagePresenter extends FollowManageContract.Presenter {
    @Override
    public void getDbProject() {
        mView.getDbProject(mModel.getDbProject(mContext));
    }
}

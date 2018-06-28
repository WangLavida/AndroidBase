package com.xgs.androidbase.ui.model;

import android.content.Context;

import com.xgs.androidbase.api.Api;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.common.rx.RxSchedulers;
import com.xgs.androidbase.dao.ProjectDaoUtil;
import com.xgs.androidbase.ui.contract.WelcomeContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/6/27.
 */

public class WelcomeModel implements WelcomeContract.Model {

    @Override
    public Observable<BaseWanBean<ProjectTreeBean>> getProjectTree() {
        return Api.createWan().getTree().compose(RxSchedulers.<BaseWanBean<ProjectTreeBean>>rxSchedulerHelper());
    }

    @Override
    public void saveProjectTree(Context mContext,List<ProjectTreeBean> projectTreeBeanList) {
        ProjectDaoUtil.init(mContext).insertProjectList(projectTreeBeanList);
    }

    @Override
    public List<ProjectTreeBean> getDbProject(Context mContext) {
        return ProjectDaoUtil.init(mContext).queryAllProject();
    }
}

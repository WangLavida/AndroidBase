package com.xgs.androidbase.ui.contract;

import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.RxBusBean;

import java.util.List;

/**
 * Created by W.J on 2018/7/3.
 */

public interface WanContract {
    interface Model extends WelcomeContract.Model {
    }

    interface View extends BaseView{
        void returnFollowProject(List<ProjectTreeBean> projectTreeBeanList);
        void addTree(ProjectTreeBean projectTreeBean);
        void removeTree(ProjectTreeBean projectTreeBean);
        void swapTree(RxBusBean rxBusBean);
    }

    abstract static class Presenter extends BasePresenter<View,Model> {
      public abstract void getFollowProject();
    }
}

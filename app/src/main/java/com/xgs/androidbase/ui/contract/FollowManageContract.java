package com.xgs.androidbase.ui.contract;

import android.content.Context;

import com.xgs.androidbase.base.BaseModel;
import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.ProjectTreeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/6/28.
 */

public interface FollowManageContract {
    interface Model extends WelcomeContract.Model {
        Observable<String> cleanProject(Context mContext);
    }

    interface View extends BaseView {
        void getDbProject(List<ProjectTreeBean> projectTreeBeanList);

        void updateProject();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getDbProject();

        public abstract void cleanProject();

        public abstract void saveProjectTree(List<ProjectTreeBean> projectTreeBeanList);
    }
}

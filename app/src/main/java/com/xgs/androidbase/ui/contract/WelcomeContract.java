package com.xgs.androidbase.ui.contract;

import android.content.Context;

import com.xgs.androidbase.base.BaseModel;
import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/6/27.
 */

public interface WelcomeContract {
    interface Model extends BaseModel {
        Observable<BaseWanBean<ProjectTreeBean>> getProjectTree();
        Observable<String> saveProjectTree(Context mContext, List<ProjectTreeBean> projectTreeBeanList);
        Observable<List<ProjectTreeBean>> getDbProject(Context mContext);
    }

    interface View extends BaseView {
        void saveProjectTree(BaseWanBean<ProjectTreeBean> baseWanBean);
        void getDbProject(List<ProjectTreeBean> projectTreeBeanList);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getProjectTree();
        public abstract void getDbProject();
        public abstract void saveProjectTree(List<ProjectTreeBean> projectTreeBeanList);
    }
}

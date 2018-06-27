package com.xgs.androidbase.ui.contract;

import com.xgs.androidbase.base.BaseModel;
import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/6/27.
 */

public interface WelcomeContract {
    interface Model extends BaseModel {
        Observable<BaseWanBean<ProjectTreeBean>> getProjectTree();
    }

    interface View extends BaseView {
        void saveProjectTree(BaseWanBean<ProjectTreeBean> baseWanBean);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getProjectTree();
    }
}

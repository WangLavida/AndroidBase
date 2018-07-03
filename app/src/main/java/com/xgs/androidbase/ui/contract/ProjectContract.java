package com.xgs.androidbase.ui.contract;

import com.xgs.androidbase.base.BaseModel;
import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * Created by W.J on 2018/7/3.
 */

public interface ProjectContract {
    interface Model extends BaseModel{
        Observable<ProjectBaseBean> getProjectList(int pageNo,Long cid);
    }

    interface View extends BaseView{
        void returnPojectList(ProjectBaseBean projectBaseBean);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void getProjectList(int pageNo,Long cid);
    }
}

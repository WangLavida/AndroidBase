package com.xgs.androidbase.ui.contract;

import com.xgs.androidbase.base.BaseModel;
import com.xgs.androidbase.base.BasePresenter;
import com.xgs.androidbase.base.BaseView;
import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.bean.GankBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by W.J on 2018/7/10.
 */

public interface GankContract {
    interface Model extends BaseModel{
        Observable<GankBaseBean> getData(int num,int pageNo);
    }

    interface View extends BaseView{
        void returnGankList(GankBaseBean gankBaseBean);
        void listToTop();
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
       public abstract void getData(int num,int pageNo);
    }
}

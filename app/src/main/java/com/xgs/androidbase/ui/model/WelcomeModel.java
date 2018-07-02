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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by W.J on 2018/6/27.
 */

public class WelcomeModel implements WelcomeContract.Model {

    @Override
    public Observable<BaseWanBean<ProjectTreeBean>> getProjectTree() {
        return Api.createWan().getTree().compose(RxSchedulers.<BaseWanBean<ProjectTreeBean>>rxSchedulerHelper());
    }

    @Override
    public Observable<String> saveProjectTree(final Context mContext, final List<ProjectTreeBean> projectTreeBeanList) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                ProjectDaoUtil.init(mContext).insertProjectList(projectTreeBeanList);
                emitter.onNext("");
                emitter.onComplete();
            }
        });

    }

    @Override
    public Observable<List<ProjectTreeBean>> getDbProject(final Context mContext) {
        return Observable.create(new ObservableOnSubscribe<List<ProjectTreeBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ProjectTreeBean>> emitter) throws Exception {
                emitter.onNext(ProjectDaoUtil.init(mContext).queryAllProject());
                emitter.onComplete();
            }
        });
    }
}

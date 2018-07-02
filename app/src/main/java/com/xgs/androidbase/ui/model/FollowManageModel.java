package com.xgs.androidbase.ui.model;

import android.content.Context;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.dao.ProjectDaoUtil;
import com.xgs.androidbase.ui.contract.FollowManageContract;
import com.xgs.androidbase.ui.contract.WelcomeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by W.J on 2018/6/28.
 */

public class FollowManageModel extends WelcomeModel implements FollowManageContract.Model {

    @Override
    public Observable<String> cleanProject(final Context mContext) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if(ProjectDaoUtil.init(mContext).deleteAll()){
                    emitter.onNext("");
                    emitter.onComplete();
                };
            }
        });
    }
}

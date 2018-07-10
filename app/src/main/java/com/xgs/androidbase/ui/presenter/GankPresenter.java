package com.xgs.androidbase.ui.presenter;

import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.contract.GankContract;

/**
 * Created by W.J on 2018/7/10.
 */

public class GankPresenter extends GankContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        RxBus.getInstance().toObservable(String.class).subscribe(new RxObserver<String>(mContext, mRxManager) {
            @Override
            public void onSuccess(String s) {
                if (s.equals(Constant.LIST_TO_TOP)) {
                    mView.listToTop();
                }
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void getData(int num, int pageNo) {
        mView.startLoad();
        mModel.getData(num, pageNo).subscribe(new RxObserver<GankBaseBean>(mContext, mRxManager) {
            @Override
            public void onSuccess(GankBaseBean gankBaseBean) {
                mView.returnGankList(gankBaseBean);
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }
}

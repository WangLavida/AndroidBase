package com.xgs.androidbase.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.util.LogUtil;

import java.lang.reflect.Field;

/**
 * Created by W.J on 2018/7/5.
 */

public class ScrollingViewBehavior extends FloatingActionButton.Behavior {

    public ScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 10 && child.getVisibility() == View.VISIBLE) {
            child.setVisibility(View.INVISIBLE);
            RxBus.getInstance().post(Constant.MENU_HIDE);
        } else if (dyConsumed < -10 && child.getVisibility() != View.VISIBLE) {
            child.show();
            RxBus.getInstance().post(Constant.MENU_SHOW);
        }
    }
}

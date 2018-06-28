package com.xgs.androidbase.util;

import com.blankj.utilcode.util.Utils;

/**
 * Created by W.J on 2018/6/28.
 */

public class DpUtil {

    public static int dp2px(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dp(final float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

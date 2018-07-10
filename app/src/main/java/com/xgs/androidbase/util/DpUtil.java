package com.xgs.androidbase.util;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
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
    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = dp2px(left);
            rightPx = dp2px(right);
            topPx = dp2px(top);
            bottomPx = dp2px(bottom);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        view.requestLayout();
        return marginParams;
    }


    /**
     * 通过比例设置图片的高度
     *
     * @param width 图片的宽
     * @param bili  图片比例
     * @param type  1:外层 LinearLayout 2：外层 RelativeLayout
     */
    public static void formartHight(ImageView imageView, int width, float bili, int type) {
        int height = (int) (width / bili);
        if (type == 1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        } else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        }
    }
}

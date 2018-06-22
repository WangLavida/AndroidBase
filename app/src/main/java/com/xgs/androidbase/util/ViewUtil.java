package com.xgs.androidbase.util;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * Created by W.J on 2018/6/22.
 */

public class ViewUtil {
    public static void dynamicSetTabLayoutMode(TabLayout tabLayout, Context mContext) {
        int tabWidth = calculateTabWidth(tabLayout);

        int screenWidth = ScreenUtils.getScreenWidth();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }
}

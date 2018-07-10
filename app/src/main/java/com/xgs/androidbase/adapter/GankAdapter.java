package com.xgs.androidbase.adapter;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.GankBean;
import com.xgs.androidbase.util.DpUtil;
import com.xgs.androidbase.util.GlideUtil;

import java.util.List;

/**
 * Created by W.J on 2018/7/10.
 */

public class GankAdapter extends BaseQuickAdapter<GankBean, BaseViewHolder> {
    public GankAdapter(int layoutResId, @Nullable List<GankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            DpUtil.setViewMargin(helper.itemView, false, 12, 6, 12, 0);
        } else {
            DpUtil.setViewMargin(helper.itemView, false, 6, 12, 12, 0);
        }
        GlideUtil.init(mContext).loadImage(item.getUrl(), (ImageView) helper.getView(R.id.gank_image));
    }
}

package com.xgs.androidbase.adapter;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.GankBean;
import com.xgs.androidbase.util.DpUtil;
import com.xgs.androidbase.util.GlideUtil;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ScreenUtils;

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
            DpUtil.setViewMargin(helper.itemView, false, 10, 5, 10, 0);
        } else {
            DpUtil.setViewMargin(helper.itemView, false, 5, 10, 10, 0);
        }
        final ImageView imageView = helper.getView(R.id.gank_image);
        Glide.with(mContext).load(item.getUrl().replace("large", "small")).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                //这个bitmap就是你图片url加载得到的结果
                //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();//获取你要填充图片的布局的layoutParam
                layoutParams.height = (int) (((float) resource.getMinimumHeight()) / resource.getMinimumWidth() * ScreenUtils.getScreenWidth(mContext) / 2);
                //因为是2列,所以宽度是屏幕的一半,高度是根据bitmap的高/宽*屏幕宽的一半
                layoutParams.width = ScreenUtils.getScreenWidth(mContext) / 2;//这个是布局的宽度
                imageView.setLayoutParams(layoutParams);//容器的宽高设置好了
                imageView.setImageDrawable(resource);
            }
        });
    }
}

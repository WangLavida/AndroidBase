package com.xgs.androidbase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.GankBean;
import com.xgs.androidbase.util.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankShowActivity extends BaseActivity {
    public static String GANK_BEAN = "GANK_BEAN";
    @BindView(R.id.image_view)
    ImageView imageView;
    private GankBean gankBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_show;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        gankBean = (GankBean) getIntent().getExtras().get(GANK_BEAN);
        GlideUtil.init(mContext).loadImage(gankBean.getUrl(),imageView);
//        Glide.with(mContext).load(gankBean.getUrl()).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
////               imageView.set
//                BitmapDrawable bd = (BitmapDrawable) resource;
//                Bitmap bitmap = bd.getBitmap();
//                imageView.setImage(ImageSource.bitmap(bitmap));
//            }
//        });
    }

    @Override
    public void initData() {

    }

    public static void startGankShow(Context context, GankBean gankBean) {
        Intent intent = new Intent(context, GankShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GANK_BEAN, gankBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

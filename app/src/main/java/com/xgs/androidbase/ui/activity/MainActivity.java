package com.xgs.androidbase.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.util.GlideUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.head_image)
    ImageView headImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        GlideUtil.init(mContext).loadCircleImage(R.mipmap.head, headImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}


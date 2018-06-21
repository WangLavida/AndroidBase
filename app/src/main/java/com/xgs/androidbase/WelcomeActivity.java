package com.xgs.androidbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

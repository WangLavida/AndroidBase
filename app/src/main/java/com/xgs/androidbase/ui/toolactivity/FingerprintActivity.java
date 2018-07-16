package com.xgs.androidbase.ui.toolactivity;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FingerprintActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.imgae_view)
    ImageView imgaeView;
    @BindView(R.id.text_view)
    TextView textView;

    private FingerprintManager fingerprintManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fingerprint;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        toolBar.setTitle("指纹识别");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgae_view)
    public void onViewClicked() {
        if (!fingerprintManager.isHardwareDetected()) {
            ToastUtil.showShort("未检测到硬件");
            return;
        }
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            ToastUtil.showShort("未录入指纹");
            return;
        }
        fingerprintManager.authenticate(null, null, 0, new FingerCallBack(), null);
        imgaeView.setImageResource(R.mipmap.fingerprint_normal);
        textView.setText("开始识别");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public class FingerCallBack extends FingerprintManager.AuthenticationCallback {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            LogUtil.i("onAuthenticationSucceeded");
            imgaeView.setImageResource(R.mipmap.fingerprint_right);
            textView.setText("识别成功");
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            LogUtil.i("onAuthenticationError");
            imgaeView.setImageResource(R.mipmap.fingerprint_error);
            textView.setText("识别失败");
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            LogUtil.i("onAuthenticationHelp");
        }


        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            LogUtil.i("onAuthenticationFailed");
            imgaeView.setImageResource(R.mipmap.fingerprint_error);
            textView.setText("识别失败");
        }
    }
}

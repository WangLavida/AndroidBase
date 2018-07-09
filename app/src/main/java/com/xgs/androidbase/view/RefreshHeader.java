package com.xgs.androidbase.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xgs.androidbase.R;
import com.xgs.androidbase.util.DpUtil;

/**
 * Created by W.J on 2018/7/9.
 */

public class RefreshHeader extends LinearLayout implements com.scwang.smartrefresh.layout.api.RefreshHeader {
    private TextView headerText;
    private LottieAnimationView xlottieView;
    private LottieAnimationView glottieView;
    private LottieAnimationView slottieView;

    public RefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public RefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context mContext) {
        setGravity(Gravity.CENTER);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header, null);
        headerText = headerView.findViewById(R.id.header_text);
        xlottieView = headerView.findViewById(R.id.xlottie_view);
        glottieView = headerView.findViewById(R.id.glottie_view);
        slottieView = headerView.findViewById(R.id.slottie_view);
        addView(headerView);
        setMinimumHeight(DpUtil.dp2px(60));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }


    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        xlottieView.cancelAnimation();
//        glottieView.cancelAnimation();
//        slottieView.cancelAnimation();
        if (success) {
            headerText.setText("刷新完成");
        } else {
            headerText.setText("刷新失败");
        }
        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                headerText.setText("下拉开始刷新");
                break;
            case Refreshing:
                headerText.setText("正在刷新");
                xlottieView.playAnimation();
                glottieView.playAnimation();
                slottieView.playAnimation();
                break;
            case ReleaseToRefresh:
                headerText.setText("释放立即刷新");
                break;
        }
    }
}

package com.xgs.androidbase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xgs.androidbase.R;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.ui.contract.FollowManageContract;
import com.xgs.androidbase.ui.model.FollowManageModel;
import com.xgs.androidbase.ui.presenter.FollowManagePresenter;
import com.xgs.androidbase.util.LogUtil;

import java.util.List;

import butterknife.BindView;

public class FollowManageActivity extends BaseActivity<FollowManagePresenter, FollowManageModel> implements FollowManageContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_manage;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        toolBar.setTitle("标签");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getDbProject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, FollowManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getDbProject(List<ProjectTreeBean> projectTreeBeanList) {
        LogUtil.i(projectTreeBeanList.size());
    }
}

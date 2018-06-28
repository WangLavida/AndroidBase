package com.xgs.androidbase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.ProjectTreeAdapter;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.ui.contract.FollowManageContract;
import com.xgs.androidbase.ui.model.FollowManageModel;
import com.xgs.androidbase.ui.presenter.FollowManagePresenter;
import com.xgs.androidbase.util.DpUtil;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FollowManageActivity extends BaseActivity<FollowManagePresenter, FollowManageModel> implements FollowManageContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.my_follow)
    RecyclerView myFollow;
    @BindView(R.id.more_follow)
    RecyclerView moreFollow;
    private List<ProjectTreeBean> myTreeList = new ArrayList<ProjectTreeBean>();
    private List<ProjectTreeBean> moreTreeList = new ArrayList<ProjectTreeBean>();
    private ProjectTreeAdapter myTreeAdapter;
    private ProjectTreeAdapter moreTreeAdapter;

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
        myTreeAdapter = new ProjectTreeAdapter(R.layout.tree_item,myTreeList,true);
        myFollow.setLayoutManager(new GridLayoutManager(this,4));
        myFollow.addItemDecoration(new GridSpacingItemDecoration(4, DpUtil.dp2px(5),false));
        myFollow.setAdapter(myTreeAdapter);

        moreTreeAdapter = new ProjectTreeAdapter(R.layout.tree_item,moreTreeList,true);
        moreFollow.setLayoutManager(new GridLayoutManager(this,4));
        moreFollow.addItemDecoration(new GridSpacingItemDecoration(4,DpUtil.dp2px(5),false));
        moreFollow.setAdapter(moreTreeAdapter);
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
        for(ProjectTreeBean projectTreeBean:projectTreeBeanList){
            if(projectTreeBean.isFollow()){
                myTreeList.add(projectTreeBean);
            }else{
                moreTreeList.add(projectTreeBean);
            }
        }
        myTreeAdapter.notifyDataSetChanged();
        moreTreeAdapter.notifyDataSetChanged();
    }
}

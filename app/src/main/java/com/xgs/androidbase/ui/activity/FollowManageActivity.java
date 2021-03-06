package com.xgs.androidbase.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.ProjectTreeAdapter;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.RxBusBean;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
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
        initMy();
        initMore();
    }

    @Override
    public void initData() {
        mPresenter.getDbProject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initMy() {
        myTreeAdapter = new ProjectTreeAdapter(R.layout.tree_item, myTreeList, true);
        myFollow.setLayoutManager(new GridLayoutManager(this, 4));
        myFollow.addItemDecoration(new GridSpacingItemDecoration(4, DpUtil.dp2px(5), false));
        myTreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!myTreeList.get(position).isFixed()) {
                    myTreeList.get(position).setFollow(false);
                    moreTreeAdapter.addData(myTreeList.get(position));
                    RxBus.getInstance().post(new RxBusBean(Constant.REMOVE_TREE, myTreeList.get(position)));
                    myTreeAdapter.remove(position);
                    mPresenter.cleanProject();
                }
            }
        });
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myTreeAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(myFollow);
        myTreeAdapter.enableDragItem(itemTouchHelper, R.id.project_name, true);
        myTreeAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                mPresenter.cleanProject();
                RxBus.getInstance().post(new RxBusBean(Constant.SWAP_TREE, myTreeList.get(to),from,to));
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        });
        myFollow.setAdapter(myTreeAdapter);
    }

    private void initMore() {
        moreTreeAdapter = new ProjectTreeAdapter(R.layout.tree_item, moreTreeList, false);
        moreFollow.setLayoutManager(new GridLayoutManager(this, 4));
        moreFollow.addItemDecoration(new GridSpacingItemDecoration(4, DpUtil.dp2px(5), false));
        moreTreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                moreTreeList.get(position).setFollow(true);
                myTreeAdapter.addData(moreTreeList.get(position));
                RxBus.getInstance().post(new RxBusBean(Constant.ADD_TREE, moreTreeList.get(position)));
                moreTreeAdapter.remove(position);
                mPresenter.cleanProject();
            }
        });
        moreFollow.setAdapter(moreTreeAdapter);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, FollowManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getDbProject(List<ProjectTreeBean> projectTreeBeanList) {
        for (ProjectTreeBean projectTreeBean : projectTreeBeanList) {
            if (projectTreeBean.isFollow()) {
                myTreeList.add(projectTreeBean);
            } else {
                moreTreeList.add(projectTreeBean);
            }
        }
        myTreeAdapter.notifyDataSetChanged();
        moreTreeAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateProject() {
        List<ProjectTreeBean> allList = new ArrayList<ProjectTreeBean>();
        allList.addAll(myTreeList);
        allList.addAll(moreTreeList);
        for (ProjectTreeBean projectTreeBean : allList) {
            projectTreeBean.setLid(null);
        }
        mPresenter.saveProjectTree(allList);
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void onError() {

    }
}

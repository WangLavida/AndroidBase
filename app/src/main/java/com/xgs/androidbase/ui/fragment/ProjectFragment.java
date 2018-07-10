package com.xgs.androidbase.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.ProjectListAdapter;
import com.xgs.androidbase.base.BaseFragment;
import com.xgs.androidbase.bean.ProjectBaseBean;
import com.xgs.androidbase.bean.ProjectBean;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.ui.contract.ProjectContract;
import com.xgs.androidbase.ui.model.ProjectModel;
import com.xgs.androidbase.ui.presenter.ProjectPresenter;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ToastUitl;
import com.xgs.androidbase.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter, ProjectModel> implements ProjectContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ARG_PARAM1";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private ProjectListAdapter projectListAdapter;
    // TODO: Rename and change types of parameters
    private ProjectTreeBean projectTreeBean;
    private List<ProjectBean> projectBeanList = new ArrayList<ProjectBean>();
    private OnFragmentInteractionListener mListener;
    private int pageNo = 1;

    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(ProjectTreeBean projectTreeBean) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, projectTreeBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            projectTreeBean = (ProjectTreeBean) getArguments().getSerializable(ARG_PARAM1);
            LogUtil.i(projectTreeBean.getName());
        }
        projectListAdapter = new ProjectListAdapter(R.layout.project_item, projectBeanList);
        projectListAdapter.isFirstOnly(false);
//        projectListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.addItemDecoration(new SpacesItemDecoration(mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.light_color)));
        recycler.setAdapter(projectListAdapter);
        initRefresh();
    }

    private void initRefresh() {
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "我也是有底线的";//"没有更多数据了";
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                mPresenter.getProjectList(pageNo, projectTreeBean.getId());
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo = pageNo + 1;
                mPresenter.getProjectList(pageNo, projectTreeBean.getId());
            }
        });

    }

    @Override
    public void initData() {
        mPresenter.getProjectList(pageNo, projectTreeBean.getId());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void returnPojectList(ProjectBaseBean projectBaseBean) {
        LogUtil.i(projectBaseBean.getData().getDatas().size());
        if (projectBaseBean.getData().getDatas().size() == 0) {
            ToastUitl.showShort("加载完毕");
           refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (pageNo == 1) {
                projectBeanList.clear();
            }
            projectBeanList.addAll(projectBaseBean.getData().getDatas());
            projectListAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void listToTop() {
        recycler.scrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

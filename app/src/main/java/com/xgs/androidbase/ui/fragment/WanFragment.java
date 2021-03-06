package com.xgs.androidbase.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.MainFragmentPagerAdapter;
import com.xgs.androidbase.base.BaseFragment;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.bean.RxBusBean;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.ui.activity.FollowManageActivity;
import com.xgs.androidbase.ui.contract.WanContract;
import com.xgs.androidbase.ui.model.WanModel;
import com.xgs.androidbase.ui.presenter.WanPresenter;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WanFragment extends BaseFragment<WanPresenter, WanModel> implements ProjectFragment.OnFragmentInteractionListener, WanContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.add_follow)
    ImageView addFollow;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<String> tabTitles = new ArrayList<String>();
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public WanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WanFragment newInstance() {
        WanFragment fragment = new WanFragment();
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_wan;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ((AppCompatActivity)mContext).setSupportActionBar(toolBar);
        toolBar.setTitle("");
        titleText.setText("首页");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.openDrawer();
            }
        });
        initViewPager();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post(Constant.LIST_TO_TOP);
            }
        });
    }

    private void initViewPager() {
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        ViewUtil.dynamicSetTabLayoutMode(tabLayout, mContext);
    }

    private ProjectFragment newFragment(ProjectTreeBean projectTreeBean) {
        ProjectFragment projectFragment = ProjectFragment.newInstance(projectTreeBean);
        return projectFragment;
    }

    @Override
    public void initData() {
        mPresenter.getFollowProject();
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
        LogUtil.i("onAttach");
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.add_follow)
    public void onViewClicked() {
        FollowManageActivity.startAction(mContext);
    }

    @Override
    public void returnFollowProject(List<ProjectTreeBean> projectTreeBeanList) {
        for (ProjectTreeBean projectTreeBean : projectTreeBeanList) {
            tabTitles.add(projectTreeBean.getName());
            fragmentList.add(newFragment(projectTreeBean));
        }
        mainFragmentPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addTree(ProjectTreeBean projectTreeBean) {
        mainFragmentPagerAdapter.addItem(newFragment(projectTreeBean), projectTreeBean.getName());
    }

    @Override
    public void removeTree(ProjectTreeBean projectTreeBean) {

        for (int i = 0; i < tabTitles.size(); i++) {
            if (tabTitles.get(i).equals(projectTreeBean.getName())) {
                mainFragmentPagerAdapter.delItem(i);
            }
        }
    }

    @Override
    public void swapTree(RxBusBean rxBusBean) {
        mainFragmentPagerAdapter.swapItems(rxBusBean.fromPos, rxBusBean.toPos);
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void onError() {

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

        void openDrawer();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.tool_search).getActionView();
        searchView.setQueryHint("你想搜什么");
        //修改搜索按钮
        AppCompatImageView sButton=(AppCompatImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        sButton.setImageResource(R.mipmap.search_icon);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment显示menu
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

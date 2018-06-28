package com.xgs.androidbase.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.MainFragmentPagerAdapter;
import com.xgs.androidbase.base.BaseFragment;
import com.xgs.androidbase.ui.activity.FollowManageActivity;
import com.xgs.androidbase.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WanFragment extends BaseFragment implements MainFragment.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.add_follow)
    ImageView addFollow;
    private List<String> tabTitles = new ArrayList<String>();
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        initViewPager();
    }

    private void initViewPager() {
        initData();
        toolBar.setTitle("");
        titleText.setText("首页");
        for (String tabTitle : tabTitles) {
            fragmentList.add(newFragment(tabTitle));
        }
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        ViewUtil.dynamicSetTabLayoutMode(tabLayout, mContext);
    }

    private MainFragment newFragment(String title) {
        MainFragment mainFragment = MainFragment.newInstance(title);
        return mainFragment;
    }

    private void initData() {
        tabTitles.add("湖人");
        tabTitles.add("雷霆");
        tabTitles.add("马刺");
        tabTitles.add("火箭");
        tabTitles.add("热火");
        tabTitles.add("骑士");
        tabTitles.add("凯尔特人");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.add_follow)
    public void onViewClicked() {
        FollowManageActivity.startAction(mContext);
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

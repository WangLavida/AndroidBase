package com.xgs.androidbase.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.MainFragmentPagerAdapter;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.ui.fragment.MainFragment;
import com.xgs.androidbase.util.GlideUtil;
import com.xgs.androidbase.util.LogUtil;
import com.xgs.androidbase.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener {
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.title_text)
    TextView titleText;
    private List<String> tabTitles = new ArrayList<String>();
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initDrawerLayout();
        initViewPager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewPager() {
        initData();
        toolBar.setTitle("");
        titleText.setText("首页");
        for (String tabTitle : tabTitles) {
            fragmentList.add(newFragment(tabTitle));
        }
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabTitles);
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

    private void initDrawerLayout() {
        View headView = navView.getHeaderView(0);
        ImageView headImage = headView.findViewById(R.id.head_image);
        GlideUtil.init(mContext).loadCircleImage(R.mipmap.head, headImage);

//        navView.setItemIconTintList(null);//图片不变色

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_main:
                        LogUtil.i("main");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_theme:
                        LogUtil.i("nav_theme");
                        break;
                    case R.id.nav_about:
                        LogUtil.i("nav_about");
                        break;
                }
                return false;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                LogUtil.i("onDrawerSlide" + slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                LogUtil.i("onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                LogUtil.i("onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                LogUtil.i("onDrawerStateChanged");
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


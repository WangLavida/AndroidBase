package com.xgs.androidbase.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.MainFragmentPagerAdapter;
import com.xgs.androidbase.base.BaseActivity;
import com.xgs.androidbase.common.Constant;
import com.xgs.androidbase.common.rx.RxBus;
import com.xgs.androidbase.common.rx.RxManager;
import com.xgs.androidbase.common.rx.RxObserver;
import com.xgs.androidbase.ui.fragment.GankFragment;
import com.xgs.androidbase.ui.fragment.ProjectFragment;
import com.xgs.androidbase.ui.fragment.ToolFragment;
import com.xgs.androidbase.ui.fragment.WanFragment;
import com.xgs.androidbase.util.GlideUtil;
import com.xgs.androidbase.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ProjectFragment.OnFragmentInteractionListener, WanFragment.OnFragmentInteractionListener, GankFragment.OnFragmentInteractionListener, ToolFragment.OnFragmentInteractionListener {
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    private List<String> tabTitles = new ArrayList<String>();
    private int[] tabIcons = {R.drawable.selector_and_image, R.drawable.selector_gank_image, R.drawable.selector_tool_image};
    private int tabLayoutHeight;

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
        RxBus.getInstance().toObservable(String.class).subscribe(new RxObserver<String>(mContext,new RxManager()) {
            @Override
            public void onSuccess(String s) {
                if (s.equals(Constant.MENU_SHOW)) {
                    LogUtil.i(Constant.MENU_SHOW);
                    setMneu(true);
                }else if (s.equals(Constant.MENU_HIDE)) {
                    LogUtil.i(Constant.MENU_HIDE);
                    setMneu(false);
                }
            }

            @Override
            public void onFail(Throwable e, boolean isNetWorkError) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();
    }

    private void initViewPager() {
        tabTitles.add("安卓");
        tabTitles.add("福利");
        tabTitles.add("工具");
        fragmentList.add(WanFragment.newInstance());
        fragmentList.add(GankFragment.newInstance());
        fragmentList.add(ToolFragment.newInstance());
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        //自定义TabLayout item样式
        //方法一
        for (int i = 0; i < tabTitles.size(); i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.tab_item, tabLayout, false);
            TextView itemText = (TextView) itemView.findViewById(R.id.item_text);
            ImageView itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemText.setText(tabTitles.get(i));
            itemImage.setImageResource(tabIcons[i]);
            tabLayout.getTabAt(i).setCustomView(itemView);
        }
        //方法二
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            Drawable d = null;
//            switch (i) {
//                case 0:
//                    d = getResources().getDrawable(R.drawable.selector_and_image);
//                    break;
//                case 1:
//                    d = getResources().getDrawable(R.drawable.selector_gank_image);
//                    break;
//                case 2:
//                    d = getResources().getDrawable(R.drawable.selector_tool_image);
//                    break;
//            }
//            tab.setIcon(d);
//        }
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

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.START);
    }

    private void setMneu(boolean isShow) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (isShow) {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        } else {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }
}


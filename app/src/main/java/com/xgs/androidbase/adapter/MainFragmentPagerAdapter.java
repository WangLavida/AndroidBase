package com.xgs.androidbase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by W.J on 2018/6/22.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragment;
    private List<String> mTitleList;

    /**
     * 普通
     */
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    /**
     * 接收首页传递的标题
     */
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }


    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 首页显示title，每日推荐等..
     * 若有问题，移到对应单独页面
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }

    public void addFragmentList(List<Fragment> fragment) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        notifyDataSetChanged();
    }

    //添加删除移动fragment
    public int delItem(String title) {
        int index = mTitleList.indexOf(title);
        if (index != -1) {
            delItem(index);
        }
        return index;
    }

    public void delItem(int position) {
        mTitleList.remove(position);
        mFragment.remove(position);
        notifyDataSetChanged();
    }

    public void swapItems(int fromPos, int toPos) {
        Collections.swap(mTitleList, fromPos, toPos);
        Collections.swap(mFragment, fromPos, toPos);
        notifyDataSetChanged();
    }

    public void addItem(Fragment fragment, String title) {
        mFragment.add(fragment);
        mTitleList.add(title);
        notifyDataSetChanged();
    }
}

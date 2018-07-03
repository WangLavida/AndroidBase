package com.xgs.androidbase.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.ProjectTreeBean;
import com.xgs.androidbase.util.LogUtil;

import java.util.List;

/**
 * Created by W.J on 2018/6/28.
 */

public class ProjectTreeAdapter extends BaseItemDraggableAdapter<ProjectTreeBean, BaseViewHolder> {
    private boolean isMy;

    public ProjectTreeAdapter(int layoutResId, @Nullable List<ProjectTreeBean> data, boolean isMy) {
        super(layoutResId, data);
        this.isMy = isMy;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTreeBean item) {
        helper.setText(R.id.project_name, item.getName());
        if(isMy){
            if(item.isFixed()){
                helper.setBackgroundRes(R.id.project_name,R.drawable.tree_item_fixed_bg);
                helper.setTextColor(R.id.project_name,mContext.getResources().getColor(R.color.white));
            }else{
                helper.setBackgroundRes(R.id.project_name,R.drawable.tree_item_bg);
                helper.setTextColor(R.id.project_name,mContext.getResources().getColor(R.color.text_title_color));
            }
        }
    }
    @Override
    public void addData(@NonNull ProjectTreeBean data) {
        getData().add(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        getData().remove(position);
        notifyDataSetChanged();
    }
}

package com.xgs.androidbase.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.ProjectTreeBean;

import java.util.List;

/**
 * Created by W.J on 2018/6/28.
 */

public class ProjectTreeAdapter extends BaseQuickAdapter<ProjectTreeBean, BaseViewHolder> {
    private boolean isMy;

    public ProjectTreeAdapter(int layoutResId, @Nullable List<ProjectTreeBean> data, boolean isMy) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTreeBean item) {
        helper.setText(R.id.project_name, item.getName());
    }
}

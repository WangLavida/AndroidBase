package com.xgs.androidbase.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.ProjectBean;
import com.xgs.androidbase.util.GlideUtil;

import java.util.List;

/**
 * Created by W.J on 2018/7/3.
 */

public class ProjectListAdapter extends BaseQuickAdapter<ProjectBean, BaseViewHolder> {
    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean item) {
        helper.setText(R.id.title_text, item.getTitle());
        helper.setText(R.id.desc_text, item.getDesc());
        helper.setText(R.id.time_text, item.getNiceDate() + "   " + item.getAuthor());
        GlideUtil.init(mContext).loadImage(item.getEnvelopePic(), (ImageView) helper.getView(R.id.envelope_img));
    }
}

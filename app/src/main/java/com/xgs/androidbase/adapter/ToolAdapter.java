package com.xgs.androidbase.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgs.androidbase.R;
import com.xgs.androidbase.bean.ToolBean;

import java.util.List;

/**
 * Created by W.J on 2018/7/11.
 */

public class ToolAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder> {
    public ToolAdapter(int layoutResId, @Nullable List<ToolBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {
        helper.setImageResource(R.id.tool_image, item.getIcon());
        helper.setText(R.id.tool_name, item.getName());
    }
}

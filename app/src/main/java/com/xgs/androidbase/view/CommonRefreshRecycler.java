package com.xgs.androidbase.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.race604.drawable.wave.WaveDrawable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xgs.androidbase.R;
import com.xgs.androidbase.impl.CrrCallBack;

import butterknife.BindView;

/**
 * Created by W.J on 2018/7/10.
 */

public class CommonRefreshRecycler extends LinearLayout {
    RecyclerView recycler;
    SmartRefreshLayout refreshLayout;
    private View emptyView;
    private View loadView;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private CrrCallBack crrCallBack;
    private BaseQuickAdapter adapter;

    public CommonRefreshRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        layoutInflater.inflate(R.layout.common_refresh_recycler, this);
        recycler = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.refresh_layout);
        initEmptyView();
        initView();
    }

    /**
     * empty点击事件
     *
     * @param crrCallBack
     */
    public void setCallBack(CrrCallBack crrCallBack) {
        this.crrCallBack = crrCallBack;
    }

    public void setEmpty() {
        if (adapter != null) {
            adapter.setEmptyView(emptyView);
        }
    }

    public void setLoad() {
        if (adapter != null) {
            adapter.setEmptyView(loadView);
        }
    }

    public void finishLoadMoreWithNoMoreData() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recycler.setLayoutManager(layoutManager);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
        recycler.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recycler.addItemDecoration(decor);
    }

    public void scrollToPosition(int position) {
        recycler.scrollToPosition(position);
    }

    public void finish() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    /**
     * 设置刷新功能开启关闭
     *
     * @param refresh
     * @param loadMore
     */
    public void setEnable(boolean refresh, boolean loadMore) {
        refreshLayout.setEnableRefresh(refresh);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(loadMore);//是否启用上拉加载功能
    }

    private void initEmptyView() {
        loadView = layoutInflater.inflate(R.layout.load_view, null);
        ImageView imageView = loadView.findViewById(R.id.load_image);
        WaveDrawable mWaveDrawable = new WaveDrawable(mContext, R.mipmap.android);
        imageView.setImageDrawable(mWaveDrawable);
        mWaveDrawable.setIndeterminate(true);
//        mWaveDrawable.setLevel(10000);
//        mWaveDrawable.setWaveAmplitude(100);
//        mWaveDrawable.setWaveLength(600);
//        mWaveDrawable.setWaveSpeed(50);
        emptyView = layoutInflater.inflate(R.layout.empty_view, null);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crrCallBack != null) {
                    crrCallBack.refresh();
                }
            }
        });
    }

    private void initView() {
        refreshLayout.setDragRate(0.5f);
        refreshLayout.setHeaderMaxDragRate(1.5f);
        refreshLayout.setDisableContentWhenLoading(true);
        refreshLayout.setDisableContentWhenRefresh(true);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "我也是有底线的";//"没有更多数据了";
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (crrCallBack != null) {
                    crrCallBack.onRefresh();
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (crrCallBack != null) {
                    crrCallBack.onLoadMore();
                }
            }
        });
    }
}

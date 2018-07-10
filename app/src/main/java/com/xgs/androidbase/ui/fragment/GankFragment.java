package com.xgs.androidbase.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.GankAdapter;
import com.xgs.androidbase.base.BaseFragment;
import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.bean.GankBean;
import com.xgs.androidbase.bean.ProjectBean;
import com.xgs.androidbase.impl.CrrCallBack;
import com.xgs.androidbase.ui.contract.GankContract;
import com.xgs.androidbase.ui.model.GankModel;
import com.xgs.androidbase.ui.presenter.GankPresenter;
import com.xgs.androidbase.util.ToastUitl;
import com.xgs.androidbase.view.CommonRefreshRecycler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GankFragment extends BaseFragment<GankPresenter,GankModel> implements GankContract.View {
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.crr_view)
    CommonRefreshRecycler crrView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int pageNo = 1;
    private int num = 15;
    private GankAdapter gankAdapter;
    private List<GankBean> gankList = new ArrayList<GankBean>();
    Unbinder unbinder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    public GankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ((AppCompatActivity)mContext).setSupportActionBar(toolBar);
        toolBar.setTitle("");
        titleText.setText("福利");

        gankAdapter = new GankAdapter(R.layout.gank_item,gankList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//定义瀑布流管理器，第一个参数是列数，第二个是方向。
        //不设置的话，图片闪烁错位，有可能有整列错位的情况。
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        crrView.setLayoutManager(layoutManager);
        crrView.setAdapter(gankAdapter);

        initRefresh();
    }
    private void initRefresh() {
        crrView.setEnable(false, false);
        crrView.setCallBack(new CrrCallBack() {
            @Override
            public void refresh() {
                mPresenter.getData(num, pageNo);
            }

            @Override
            public void onRefresh() {
                pageNo = 1;
                mPresenter.getData(num, pageNo);
            }

            @Override
            public void onLoadMore() {
                pageNo = pageNo + 1;
                mPresenter.getData(num, pageNo);
            }
        });
    }
    @Override
    public void initData() {
        mPresenter.getData(num,pageNo);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void startLoad() {
        if (gankList.size() == 0) {
            crrView.setLoad();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void returnGankList(GankBaseBean gankBaseBean) {
        if (gankBaseBean.getResults().size() == 0) {
            if (gankList.size() == 0) {
                crrView.setEmpty();
            } else {
                ToastUitl.showShort("加载完毕");
            }
            crrView.finishLoadMoreWithNoMoreData();
        } else {
            if (pageNo == 1) {
                gankList.clear();
                crrView.setEnable(true, true);
            }
            gankList.addAll(gankBaseBean.getResults());
            gankAdapter.notifyDataSetChanged();
        }
        crrView.finish();
    }

    @Override
    public void listToTop() {
        crrView.scrollToPosition(0);
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

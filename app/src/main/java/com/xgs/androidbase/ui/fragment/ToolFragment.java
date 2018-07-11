package com.xgs.androidbase.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xgs.androidbase.R;
import com.xgs.androidbase.adapter.ToolAdapter;
import com.xgs.androidbase.base.BaseFragment;
import com.xgs.androidbase.bean.ToolBean;

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
 * Use the {@link ToolFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToolFragment extends BaseFragment {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ToolAdapter toolAdapter;
    private List<ToolBean> toolList = new ArrayList<ToolBean>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public ToolFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToolFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToolFragment newInstance() {
        ToolFragment fragment = new ToolFragment();
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
        return R.layout.fragment_tool;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ((AppCompatActivity) mContext).setSupportActionBar(toolBar);
        toolBar.setTitle("工具");
        toolAdapter = new ToolAdapter(R.layout.tool_item, toolList);
        recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycler.setAdapter(toolAdapter);
    }

    @Override
    public void initData() {
        toolList.add(new ToolBean(0, "指纹识别", R.mipmap.ic_launcher));
        toolAdapter.notifyDataSetChanged();
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

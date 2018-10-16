package com.xgs.androidbase.view;

import android.app.Dialog;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.xgs.androidbase.R;

/**
 * Created by W.J on 2018/10/16.
 */

public class NumProDialog extends Dialog {
    private NumberProgressBar numberProgressBar;
    private Context mContext;
    public NumProDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public NumProDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        initView();
    }
    private void initView(){
        LayoutInflater layoutInflater  = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.update_dialog_layout,null);
        setContentView(v);
        numberProgressBar = v.findViewById(R.id.num_pro);
    }
    public void setProgress(int progress){
        numberProgressBar.setProgress(progress);
    }
}

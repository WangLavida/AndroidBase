package com.xgs.androidbase.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by W.J on 2018/6/21.
 */

public class GlideUtil {
    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
    private Context mContext;

    public  GlideUtil(Context mContext) {
        this.mContext = mContext;
    }
    public static GlideUtil init(Context mContext){
        return new GlideUtil(mContext);
    }
    public void loadImage(String url, ImageView imageView) {
        Glide.with(mContext).asBitmap().load(url).apply(options).into(imageView);
    }
    public void loadCircleImage(int url, ImageView imageView) {
        Glide.with(mContext).asBitmap().load(url).apply(options).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }
}

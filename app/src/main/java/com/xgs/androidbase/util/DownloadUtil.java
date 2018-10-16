package com.xgs.androidbase.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;
import com.xgs.androidbase.api.Api;
import com.xgs.androidbase.api.UpdateService;
import com.xgs.androidbase.bean.GankBaseBean;
import com.xgs.androidbase.common.rx.RxSchedulers;
import com.xgs.androidbase.impl.DownloadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by W.J on 2018/10/16.
 */

public class DownloadUtil {
    private static final String TAG = "DownloadUtil";
    private static final String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/DownloadFile/";
    //视频下载相关
    protected UpdateService mApi;
    private Observable<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mVideoPath; //下载到本地的视频路径

    public DownloadUtil() {
        if (mApi == null) {
            mApi = Api.createApi(UpdateService.class, UpdateService.HOST);
        }
    }

    public void downloadFile(String url, final DownloadListener downloadListener) {
        LogUtil.i("downloadFile");
        //通过Url得到保存到本地的文件名
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
//            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
//            if (i != -1) {
//                name = name.substring(i);
//                mVideoPath = PATH_CHALLENGE_VIDEO +
//                        name;
//            }
            mVideoPath = PATH_CHALLENGE_VIDEO + name;
        }
        if(FileUtils.isFileExists(new File(mVideoPath))){
            FileUtils.deleteFile(new File(mVideoPath));
        }
        LogUtil.i(mVideoPath);
        if (TextUtils.isEmpty(mVideoPath)) {
            Log.e(TAG, "downloadVideo: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mVideoPath);
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
            if (mApi == null) {
                Log.e(TAG, "downloadVideo: 下载接口为空了");
                return;
            }
            mCall = mApi.downloadFile(url);
            mCall.compose(RxSchedulers.<ResponseBody>rxSchedulerHelper()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    writeFile2Disk(responseBody, mFile, downloadListener);
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.i("网络错误");
                    downloadListener.onDownloadFailure("网络错误！");
                }

                @Override
                public void onComplete() {
//                    downloadListener.onDownloadFinish(mVideoPath);
                }
            });
//            mCall.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
//                    //下载文件放在子线程
//                    mThread = new Thread() {
//                        @Override
//                        public void run() {
//                            super.run();
//                            //保存到本地
//                            writeFile2Disk(response, mFile, downloadListener);
//                        }
//                    };
//                    mThread.start();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    downloadListener.onFailure("网络错误！");
//                }
//            });
        } else {
            downloadListener.onDownloadFinish(mVideoPath);
        }
    }

    private void writeFile2Disk(ResponseBody response, File file, DownloadListener downloadListener) {
        downloadListener.onDownloadStart();
        long currentLength = 0;
        OutputStream os = null;

        if (response == null) {
            downloadListener.onDownloadFailure("资源错误！");
            return;
        }
        InputStream is = response.byteStream();
        long totalLength = response.contentLength();

        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e(TAG, "当前进度: " + currentLength);
                downloadListener.onDownloadProgress((int) (100 * currentLength / totalLength));
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onDownloadFinish(mVideoPath);
                }
            }
        } catch (FileNotFoundException e) {
            downloadListener.onDownloadFailure("未找到文件！");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onDownloadFailure("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

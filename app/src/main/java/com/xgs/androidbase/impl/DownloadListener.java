package com.xgs.androidbase.impl;

/**
 * Created by W.J on 2018/10/16.
 */

public interface DownloadListener {
    void onDownloadStart();

    void onDownloadProgress(int currentLength);

    void onDownloadFinish(String localPath);

    void onDownloadFailure(String erroInfo);
}

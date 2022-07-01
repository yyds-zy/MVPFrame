package com.free.fileupload.contract;

import android.content.Context;

import java.io.File;

public interface UpLoadContract {
    interface UpLoadPresenter {
        void upLoadFile(File file);
        void showFileList(Context context,int currentPage,int pageSize);
    }

    interface UpLoadModel {
        interface OnRequestListener {
            void onSuccess(String data);

            void onFail(String msg);
        }

        void showFileList(Context context,int currentPage, int pageSize, OnRequestListener listener);

        void upLoadFile(File file, OnRequestListener listener);
    }

    interface UpLoadView {
        void showProgress();
        void hideProgress();
        void upLoadSuccess(String data);
        void upLoadFail(String data);
        void showFileSuccess(String data);
        void showFileFail(String data);
    }
}

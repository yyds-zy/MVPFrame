package com.free.fileupload.contract;

import java.io.File;

public interface UpLoadContract {
    interface UpLoadPresenter {
        void upLoadFile(File file);
        void showFileList(int currentPage,int pageSize);
    }

    interface UpLoadModel {
        interface OnRequestListener {
            void onSuccess(String data);

            void onFail(String msg);
        }

        void showFileList(int currentPage,int pageSize,OnRequestListener listener);

        void upLoadFile(File file, OnRequestListener listener);
    }

    interface UpLoadView {
        void showProgress();
        void hideProgress();
        void upLoadSuccess(String data);
        void upLoadFail(String data);
    }
}

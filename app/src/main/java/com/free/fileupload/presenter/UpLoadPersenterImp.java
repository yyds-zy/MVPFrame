package com.free.fileupload.presenter;

import android.content.Context;

import com.free.fileupload.contract.UpLoadContract;

import java.io.File;

public class UpLoadPersenterImp implements UpLoadContract.UpLoadPresenter,UpLoadContract.UpLoadModel.OnRequestListener {

    private UpLoadContract.UpLoadModel model;
    private UpLoadContract.UpLoadView view;

    public UpLoadPersenterImp(UpLoadContract.UpLoadModel model,UpLoadContract.UpLoadView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void upLoadFile(File file) {
        view.showProgress();
        model.upLoadFile(file,this);
    }

    @Override
    public void showFileList(Context context,int currentPage, int pageSize) {
        view.showProgress();
        model.showFileList(context,currentPage,pageSize,this);
    }

    @Override
    public void onSuccess(String data) {
        view.hideProgress();
        view.upLoadSuccess(data);
    }

    @Override
    public void onFail(String msg) {
        view.hideProgress();
        view.upLoadFail(msg);
    }
}

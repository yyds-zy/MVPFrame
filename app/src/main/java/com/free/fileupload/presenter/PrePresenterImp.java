package com.free.fileupload.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.model.PreViewModelImp;

public class PrePresenterImp implements PreViewContract.Presenter ,PreViewContract.OnLoadingListener{

    PreViewContract.View preView;
    PreViewContract.Model preModel;

    public PrePresenterImp(PreViewContract.View view){
        preView = view;
        preModel = new PreViewModelImp();
    }

    @Override
    public void loadPic(Context context, String pic_url, ImageView imageView) {
        preModel.loadPic(context,pic_url,imageView,this);
    }

    @Override
    public void loading() {
        if (preView != null) {
            preView.showMsg();
        }
    }
}

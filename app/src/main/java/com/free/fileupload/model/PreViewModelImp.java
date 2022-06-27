package com.free.fileupload.model;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.util.MyBitmapUtils;

public class PreViewModelImp implements PreViewContract.Model {

    @Override
    public void loadPic(Context context, String pic_url, ImageView imageView, PreViewContract.OnLoadingListener loadingListener) {
        loadingListener.loading();
        //Glide.with(context).load(pic_url).into(imageView);
        MyBitmapUtils myBitmapUtils = new MyBitmapUtils(context);
        myBitmapUtils.disPlay(imageView,pic_url);
    }
}

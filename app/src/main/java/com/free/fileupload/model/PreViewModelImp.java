package com.free.fileupload.model;

import android.content.Context;
import android.widget.ImageView;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.util.MyBitmapUtils;

public class PreViewModelImp implements PreViewContract.Model {

    @Override
    public void loadPic(Context context, String pic_url, ImageView imageView, PreViewContract.OnLoadingListener loadingListener) {
        loadingListener.loading();
        MyBitmapUtils.getInstance().setContext(context).disPlay(imageView,pic_url);
    }
}

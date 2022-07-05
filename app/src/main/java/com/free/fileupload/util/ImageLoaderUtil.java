package com.free.fileupload.util;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class ImageLoaderUtil extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        MyBitmapUtils.getInstance().setContext(context).disPlay(imageView, (String) path);
    }
}

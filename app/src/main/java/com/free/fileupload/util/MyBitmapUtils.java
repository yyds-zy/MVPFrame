package com.free.fileupload.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;
public class MyBitmapUtils {
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private Context mContext;

    public MyBitmapUtils(Context context){
        mContext = context;

        mLocalCacheUtils=new LocalCacheUtils(context);
        mNetCacheUtils=new NetCacheUtils(mContext,mLocalCacheUtils);
    }

    public void disPlay(ImageView imageView,String url){
        //先去内存中找
        Bitmap bitmapToMemory = MemoryCacheUtils.getInstance().getBitmapFromMemCache(url);
        if (bitmapToMemory != null) {
            imageView.setImageBitmap(bitmapToMemory);
            Toast.makeText(mContext,"内存中找到了",Toast.LENGTH_SHORT).show();
            return;
        }

        //如果内存没有再去内部存储缓存
        Bitmap bitmapToLocal = mLocalCacheUtils.getBitmapToLocal(url);
        if (bitmapToLocal != null) {
            imageView.setImageBitmap(bitmapToLocal);
            Toast.makeText(mContext,"磁盘中找到了",Toast.LENGTH_SHORT).show();
            MemoryCacheUtils.getInstance().addBitmapToMemoryCache(url,bitmapToLocal);
            return;
        }

        //如果内部存储没有再去网络缓存
        mNetCacheUtils.getBitmapForNet(url,imageView);
    }
}

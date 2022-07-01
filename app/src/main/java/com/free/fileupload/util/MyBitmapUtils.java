package com.free.fileupload.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;
public class MyBitmapUtils {
    private Context mContext;

    private static MyBitmapUtils instance;

    private MyBitmapUtils(){

    }

    public static MyBitmapUtils getInstance() {
        if (instance == null) {
            synchronized (MyBitmapUtils.class) {
                if (instance == null) {
                    instance = new MyBitmapUtils();
                }
            }
        }
        return instance;
    }

    public MyBitmapUtils setContext(Context context){
        mContext = context;
        return this;
    }

    public void disPlay(ImageView imageView,String url){
        //先去内存中找
        Bitmap bitmapToMemory = MemoryCacheUtils.getInstance().getBitmapFromMemCache(url);
        if (bitmapToMemory != null) {
            imageView.setImageBitmap(bitmapToMemory);
            return;
        }

        //如果内存没有再去内部存储缓存
        Bitmap bitmapToLocal = LocalCacheUtils.getInstance().setPath(mContext).getBitmapToLocal(url);
        if (bitmapToLocal != null) {
            imageView.setImageBitmap(bitmapToLocal);
            MemoryCacheUtils.getInstance().addBitmapToMemoryCache(url,bitmapToLocal);
            return;
        }
        //如果内部存储没有再去网络缓存
        NetCacheUtils.getInstance().setContext(mContext).getBitmapForNet(url,imageView);
    }
}

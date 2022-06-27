package com.free.fileupload.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;

/***
 * 内存缓存
 */
public class MemoryCacheUtils {

    private static MemoryCacheUtils instance;
    private LruCache<String, Bitmap> mLruCache;
    private MemoryCacheUtils(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        Log.d("l_zyuanxue",cacheSize+"----- cacheSize ");

        mLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount() / 1024;
                Log.d("l_zyuanxue",byteCount+"");
                return byteCount;
            }
        };
    }

    public static MemoryCacheUtils getInstance() {
        if (instance == null) {
            synchronized (MemoryCacheUtils.class) {
                if (instance == null) {
                    instance = new MemoryCacheUtils();
                }
            }
        }
        return instance;
    }




    public void addBitmapToMemoryCache(String name,Bitmap bitmap){
        if (getBitmapFromMemCache(name) == null) {
            mLruCache.put(name, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        int size = mLruCache.size();
        Log.d("l_zyuanxue",size+"");
        return mLruCache.get(key);
    }
}

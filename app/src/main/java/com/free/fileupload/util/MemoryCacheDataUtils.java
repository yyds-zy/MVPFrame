package com.free.fileupload.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/***
 * 内存缓存
 */
public class MemoryCacheDataUtils {

    private LruCache<String, String> mLruCache;

    public MemoryCacheDataUtils(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        Log.d("l_zyuanxue",cacheSize+"----- cacheSize ");

        mLruCache = new LruCache<String, String>(cacheSize);
    }

    public void addBitmapToMemoryCache(String name,String data){
        if (getBitmapFromMemCache(name) == null) {
            mLruCache.put(name, data);
        }
    }

    public String getBitmapFromMemCache(String key) {
        int size = mLruCache.size();
        Log.d("l_zyuanxue",size+"");
        return mLruCache.get(key);
    }
}

package com.free.fileupload.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地磁盘缓存
 */
public class LocalCacheUtils {

    public String CACHE_PATH;

    public LocalCacheUtils(Context context) {
        CACHE_PATH = context.getCacheDir().getAbsolutePath()+"/bitmaps";
        //CACHE_PATH = context.getExternalCacheDir().getAbsolutePath()+"/bitmaps";
    }

    public void setBitmapToLocal(String name, Bitmap bitmap){
        try {
            String encode = MD5Encoder.encode(name);
            File file = new File(CACHE_PATH,encode);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmapToLocal(String name){
        try {
            String encode = MD5Encoder.encode(name);
            File file = new File(CACHE_PATH,encode);
            File parentFile = file.getParentFile();
            if (parentFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

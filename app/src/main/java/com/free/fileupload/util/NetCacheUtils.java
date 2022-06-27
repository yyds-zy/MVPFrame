package com.free.fileupload.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存
 */
public class NetCacheUtils {

    private LocalCacheUtils localCacheUtils;
    private Context mContext;

    public NetCacheUtils(Context context,LocalCacheUtils localCacheUtils){
        mContext = context;
        this.localCacheUtils = localCacheUtils;
    }

    public void getBitmapForNet(String pic_url, ImageView imageView) {
        new BitmapTask().execute(imageView,pic_url);
    }

    class BitmapTask extends AsyncTask<Object ,Void , Bitmap> {
        private ImageView iv_image;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... objects) {
            iv_image = (ImageView) objects[0];
            url = (String) objects[1];
            return downLoadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv_image.setImageBitmap(bitmap);
            Toast.makeText(mContext,"网络缓存中找到了",Toast.LENGTH_SHORT).show();
            localCacheUtils.setBitmapToLocal(url,bitmap);
            //这里不对  需要优化
            MemoryCacheUtils.getInstance().addBitmapToMemoryCache(url,bitmap);
        }
    }

    private Bitmap downLoadBitmap(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //图片压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=1;//宽高压缩为原来的1/2
                options.inPreferredConfig=Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

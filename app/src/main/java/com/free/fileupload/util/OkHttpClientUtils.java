package com.free.fileupload.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientUtils {

     private static OkHttpClient okHttpClient;

     public synchronized static OkHttpClient getOkHttpClient(){
         if (okHttpClient == null) {
             okHttpClient = new OkHttpClient.Builder()
                     .connectTimeout(60, TimeUnit.SECONDS)
                     .readTimeout(60,TimeUnit.SECONDS)
                     .build();
         }
         return okHttpClient;
     }
}

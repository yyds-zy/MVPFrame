package com.free.fileupload.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

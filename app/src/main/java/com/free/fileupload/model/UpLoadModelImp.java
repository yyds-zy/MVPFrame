package com.free.fileupload.model;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.free.fileupload.contract.ResCodeDef;
import com.free.fileupload.contract.UpLoadContract;
import com.free.fileupload.contract.UrlDef;
import com.free.fileupload.util.OkHttpClientUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpLoadModelImp implements UpLoadContract.UpLoadModel {

    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("image/*");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient mClient = OkHttpClientUtils.getOkHttpClient();
    private OnRequestListener mListener;

    @Override
    public void showFileList(int currentPage,int pageSize,OnRequestListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("currentPage",currentPage);
            jsonObject.put("pageSize",pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON,jsonObject.toString());
        RequestBody body = new MultipartBody.Builder()
                .addPart(requestBody)
                .build();
        Request request = new Request.Builder()
                .url(UrlDef.URL_QUERY_FILE_LIST)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener = listener;
                handler.sendEmptyMessage(1);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mListener = listener;
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String string = response.body().string();
                Message message = new Message();
                message.obj = string;
                message.what = 0;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void upLoadFile(File file, OnRequestListener listener) {
        if (file.exists()) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_FILE,file);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",file.getName(),requestBody)
                    .build();
            Request request = new Request.Builder()
                    .url(UrlDef.URL_UPLOAD_FILE)
                    .post(body)
                    .build();
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mListener = listener;
                    handler.sendEmptyMessage(1);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mListener = listener;
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Message message = new Message();
                    message.obj = response.body().string();
                    message.what = 0;
                    handler.sendMessage(message);
                }
            });
        } else {
            listener.onFail(ResCodeDef.FILE_NOT_EXIST);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mListener.onSuccess((String) msg.obj);
            }else {
                mListener.onFail(ResCodeDef.REQUEST_UPLOAD_DEFAULT);
            }
        }
    };
}

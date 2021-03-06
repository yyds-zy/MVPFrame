package com.free.fileupload.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.free.fileupload.contract.ResCodeDef;
import com.free.fileupload.contract.UpLoadContract;
import com.free.fileupload.contract.UrlDef;
import com.free.fileupload.util.FileUtils;
import com.free.fileupload.util.OkHttpClientUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    private File mFile;
    private Context mContext;

    @Override
    public void showFileList(Context context,int currentPage,int pageSize,OnRequestListener listener) {
        mContext = context;
        RequestBody requestBody = new FormBody.Builder()
                .add("currentPage", String.valueOf(currentPage))
                .add("pageSize", String.valueOf(pageSize))
                .build();
        Request request = new Request.Builder()
                .url(UrlDef.BASE_DEBUG_URL+"queryFileList")
                .post(requestBody)
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
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String string = response.body().string();
                    FileUtils.createJsonFile(string,mContext.getExternalFilesDir(null).getAbsolutePath());
                    Message message = new Message();
                    message.obj = string;
                    message.what = 0;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @Override
    public void upLoadFile(File file, OnRequestListener listener) {
        if (file.exists()) {
            mFile = file;
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_FILE,file);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",file.getName(),requestBody)
                    .build();
            Request request = new Request.Builder()
                    .url(UrlDef.BASE_DEBUG_URL+"uploadLog")
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

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mFile != null) {
                File file = new File(mFile.getPath());
                if (file.exists()) {
                    file.delete();
                }
            }
            if (msg.what == 0) {
                mListener.onSuccess((String) msg.obj);
            }else {
                mListener.onFail(ResCodeDef.REQUEST_UPLOAD_DEFAULT);
            }
        }
    };



}

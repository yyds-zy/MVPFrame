package com.free.fileupload.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.free.fileupload.R;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.presenter.PrePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewActivity extends AppCompatActivity implements PreViewContract.View {

    @BindView(R.id.image)
    ImageView image;
    PreViewContract.Presenter presenter;
    public static final String TAG = "PreviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        String pic_url = getIntent().getStringExtra("pic_url");
        presenter = new PrePresenterImp(this);
        Log.d(TAG,"http://log.free.svipss.top/Log/showFile?uin="+pic_url);
        presenter.loadPic(this,"http://log.free.svipss.top/Log/showFile?uin="+pic_url,image);
    }

    @Override
    public void showMsg() {
        Toast.makeText(this,R.string.start_loading,Toast.LENGTH_SHORT).show();
    }
}

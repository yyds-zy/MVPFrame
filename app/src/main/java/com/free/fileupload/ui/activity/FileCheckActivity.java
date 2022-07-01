package com.free.fileupload.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.free.fileupload.R;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.presenter.PrePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileCheckActivity extends AppCompatActivity{

    @BindView(R.id.wv_context)
    WebView wvContext;
    PreViewContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_check);
        ButterKnife.bind(this);
        String file = getIntent().getStringExtra("file");
        wvContext.loadUrl("http://log.free.svipss.top/Log/showFile?uin=" + file);
    }

}

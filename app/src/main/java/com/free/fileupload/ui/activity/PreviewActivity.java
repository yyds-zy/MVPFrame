package com.free.fileupload.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.fileupload.R;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.contract.UrlDef;
import com.free.fileupload.presenter.PrePresenterImp;
import com.free.fileupload.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewActivity extends BaseActivity implements PreViewContract.View {

    @BindView(R.id.image)
    ImageView image;
    PreViewContract.Presenter presenter;
    public static final String TAG = "PreviewActivity";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.meau)
    ImageView meau;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void init() {
        title.setText("文章");
        String pic_url = getIntent().getStringExtra("pic_url");
        presenter = new PrePresenterImp(this);
        presenter.loadPic(this, UrlDef.BASE_DEBUG_URL + "showFile?uin=" + pic_url, image);
    }

    @Override
    public void showMsg() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

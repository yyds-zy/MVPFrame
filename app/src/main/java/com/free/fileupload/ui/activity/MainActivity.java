package com.free.fileupload.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.fileupload.R;
import com.free.fileupload.contract.LoginView;
import com.free.fileupload.contract.UpLoadContract;
import com.free.fileupload.model.UpLoadModelImp;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.presenter.UpLoadPersenterImp;
import com.free.fileupload.ui.BaseActivity;
import com.free.fileupload.ui.adapter.FileListAdapter;
import com.free.fileupload.ui.view.SwipeFlushView;
import com.free.fileupload.util.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoginView, UpLoadContract.UpLoadView {

    public static final int GALLERY = 2;
    @BindView(R.id.btn_up_pic)
    Button btnUpPic;
    @BindView(R.id.sp_btn)
    Button btnFindFileList;
    @BindView(R.id.file_list)
    ListView fileList;
    @BindView(R.id.srl_swipe_refresh_layout)
    SwipeFlushView srlSwipeRefreshLayout;
    @BindView(R.id.mmkv_btn)
    Button mmkvBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.meau)
    ImageView meau;

    private List<FileBean.DataBean> mFileData;
    private UpLoadContract.UpLoadPresenter upLoadPresenter;
    private FileListAdapter adapter;
    private int currentPager = 1;
    private int pageSize = 20;
    private String mData;
    private boolean isClick;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                isClick = false;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        title.setText("新闻列表");
        meau.setVisibility(View.VISIBLE);
        mFileData = new ArrayList<>();
        String json = FileUtils.readJson(getExternalFilesDir(null).getAbsolutePath());
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            FileBean fileBean = gson.fromJson(json, FileBean.class);
            mFileData.clear();
            List<FileBean.DataBean> dataBeans = fileBean.getData();
            mFileData.addAll(dataBeans);
        }
        adapter = new FileListAdapter(mFileData, this);
        upLoadPresenter = new UpLoadPersenterImp(new UpLoadModelImp(), this);
        upLoadPresenter.showFileList(MainActivity.this, currentPager, pageSize);

        fileList.setAdapter(adapter);
        fileList.setOnItemClickListener((adapterView, view, i, l) -> {
            if (isClick) return;
            isClick = true;
            handler.sendEmptyMessageDelayed(0,1000);
            String fileExtension = mFileData.get(i).getFileExtension();
            if (fileExtension.equals("jpg") || fileExtension.equals("png")) {
                Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                intent.putExtra("pic_url", mFileData.get(i).getUin());
                startActivity(intent);
            } else if (fileExtension.equals("log") || fileExtension.equals("xml") || fileExtension.equals("txt") || fileExtension.equals("log") || fileExtension.equals("docx") || fileExtension.equals("pdf")) {
                Intent intent = new Intent(MainActivity.this, FileCheckActivity.class);
                intent.putExtra("file", mFileData.get(i).getUin());
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "暂不支持此文件类型", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
        srlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlSwipeRefreshLayout.setRefreshing(false);
                upLoadPresenter.showFileList(MainActivity.this, currentPager, pageSize);
            }
        });

        srlSwipeRefreshLayout.setOnLoadListener(new SwipeFlushView.OnLoadListener() {
            @Override
            public void onLoad() {
                srlSwipeRefreshLayout.setLoading(false);
                currentPager++;
                upLoadPresenter.showFileList(MainActivity.this, currentPager, pageSize);
            }
        });
    }


    @OnClick({R.id.btn_up_pic, R.id.sp_btn, R.id.mmkv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_up_pic:
                chooseFromGallery();
                break;
            case R.id.sp_btn:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY:
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    File file = FileUtils.uriToFileApiQ(imageUri, this);
                    upLoadPresenter.upLoadFile(file);
                }
                break;
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void upLoadSuccess(String data) {
        Toast.makeText(MainActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void upLoadFail(String data) {
        Toast.makeText(MainActivity.this, "上传文件失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFileSuccess(String data) {
        Gson gson = new Gson();
        FileBean fileBean = gson.fromJson(data, FileBean.class);
        List<FileBean.DataBean> dataBeans = fileBean.getData();
        if (dataBeans.size() > 0) {
            mFileData.clear();
        }
        mFileData.addAll(dataBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showFileFail(String data) {
        String json = FileUtils.readJson(getExternalFilesDir(null).getAbsolutePath());
        if (TextUtils.isEmpty(json)) return;
        Gson gson = new Gson();
        FileBean fileBean = gson.fromJson(json, FileBean.class);
        mFileData.clear();
        List<FileBean.DataBean> dataBeans = fileBean.getData();
        mFileData.addAll(dataBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.meau)
    public void onViewClicked() {
        if (btnUpPic.getVisibility() == View.GONE) {
            btnUpPic.setVisibility(View.VISIBLE);
        } else {
            btnUpPic.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
    }
}

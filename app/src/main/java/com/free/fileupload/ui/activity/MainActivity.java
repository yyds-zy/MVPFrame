package com.free.fileupload.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.fileupload.R;
import com.free.fileupload.contract.LoginView;
import com.free.fileupload.contract.UpLoadContract;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.model.UpLoadModelImp;
import com.free.fileupload.presenter.UpLoadPersenterImp;
import com.free.fileupload.ui.adapter.FileListAdapter;
import com.free.fileupload.ui.view.SwipeFlushView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoginView, UpLoadContract.UpLoadView {

    public static final int GALLERY = 2;
    @BindView(R.id.btn_up_pic)
    Button btnUpPic;
    @BindView(R.id.btn_find_file_list)
    Button btnFindFileList;
    @BindView(R.id.file_list)
    ListView fileList;
    @BindView(R.id.srl_swipe_refresh_layout)
    SwipeFlushView srlSwipeRefreshLayout;

    private List<FileBean.DataBean> mFileData;

    private UpLoadContract.UpLoadPresenter upLoadPresenter;
    private FileListAdapter adapter;
    private int currentPager = 1;
    private int pageSize = 20;
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFileData = new ArrayList<>();
        adapter = new FileListAdapter(mFileData,this);
        //  loginPresent = new LoginPresentImpl(new LoginModelImpl(),this);

        upLoadPresenter = new UpLoadPersenterImp(new UpLoadModelImp(), this);
        upLoadPresenter.showFileList(currentPager,pageSize);

        fileList.setAdapter(adapter);
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,PreviewActivity.class);
                intent.putExtra("pic_url",mFileData.get(i).getUin());
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();
        srlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upLoadPresenter.showFileList(currentPager,pageSize);
            }
        });

        srlSwipeRefreshLayout.setOnLoadListener(new SwipeFlushView.OnLoadListener() {
            @Override
            public void onLoad() {
                upLoadPresenter.showFileList(currentPager,pageSize);
            }
        });
    }


    @OnClick({R.id.btn_up_pic, R.id.btn_find_file_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_up_pic:
                chooseFromGallery();
                break;
            case R.id.btn_find_file_list:
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
                    Bitmap imageBitmap = null;
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap newImageBitmap = scaleBitmap(imageBitmap,(float)0.5);
                    Uri newUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),newImageBitmap,"IMG"+ Calendar.getInstance().getTime(),null));
                    File file = uriToFileApiQ(newUri, this);
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
        Log.d("v_zyuanxue",data);
        Gson gson=new Gson();
        FileBean fileBean = gson.fromJson(data, FileBean.class);
        List<FileBean.DataBean> dataBeans = fileBean.getData();
        mFileData.addAll(dataBeans);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void upLoadFail(String data) {
        Log.d("xzy",data);
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if (uri == null) return file;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * todo 压缩图片
     * @param origin
     * @param ratio
     * @return
     */
    public Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }
}

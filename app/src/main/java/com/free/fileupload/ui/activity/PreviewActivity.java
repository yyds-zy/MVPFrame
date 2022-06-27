package com.free.fileupload.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.free.fileupload.R;
import com.free.fileupload.contract.PreViewContract;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.presenter.PrePresenterImp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity implements PreViewContract.View {

    @BindView(R.id.image)
    ImageView image;
    PreViewContract.Presenter presenter;
    public static final String TAG = "PreviewActivity";
    @BindView(R.id.up_pic)
    Button upPic;
    @BindView(R.id.next_pic)
    Button nextPic;
    private int position;
    private ArrayList<FileBean.DataBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        String pic_url = getIntent().getStringExtra("pic_url");
        position = getIntent().getIntExtra("position", 0);
        Bundle bundle =  getIntent().getBundleExtra("bundle");
        list = bundle.getParcelableArrayList("list");
        presenter = new PrePresenterImp(this);
        presenter.loadPic(this, "http://log.free.svipss.top/Log/showFile?uin=" + pic_url, image);
    }

    @Override
    public void showMsg() {
        Toast.makeText(this, R.string.start_loading, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.up_pic, R.id.next_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.up_pic:
                if (position == 0) {
                    Toast.makeText(this, "没有上一张", Toast.LENGTH_SHORT).show();
                    return;
                }
                position --;
                presenter.loadPic(this, "http://log.free.svipss.top/Log/showFile?uin=" + list.get(position).getUin(), image);
                break;
            case R.id.next_pic:
                int size = list.size();
                if (position+1 >= size) {
                    Toast.makeText(this, "没有下一张", Toast.LENGTH_SHORT).show();
                    return;
                }
                position ++;
                presenter.loadPic(this, "http://log.free.svipss.top/Log/showFile?uin=" + list.get(position).getUin(), image);
                break;
        }
    }
}

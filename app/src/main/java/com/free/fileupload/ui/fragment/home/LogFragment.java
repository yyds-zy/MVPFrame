package com.free.fileupload.ui.fragment.home;

import androidx.recyclerview.widget.RecyclerView;
import com.free.fileupload.R;
import com.free.fileupload.contract.UpLoadContract;
import com.free.fileupload.model.UpLoadModelImp;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.presenter.UpLoadPersenterImp;
import com.free.fileupload.ui.BaseFragment;
import com.free.fileupload.ui.adapter.RecycleViewAdapter;
import com.free.fileupload.util.ImageLoaderUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import butterknife.BindView;

public class LogFragment extends BaseFragment implements UpLoadContract.UpLoadView {

    List<String> imgList = new ArrayList<>();
    List<FileBean.DataBean> dataBeanList;
    @BindView(R.id.banner)
    Banner mbanner;
    @BindView(R.id.rvRegisterList)
    RecyclerView rvRegisterList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ImageLoaderUtil imageLoaderUtil;
    private RecycleViewAdapter recycleViewAdapter;
    private UpLoadContract.UpLoadPresenter upLoadPresenter;

    private int totalSize = 0;
    private int currentPager = 1;
    private int pageSize = 10;
    private int recycleViewState = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_layout_home_log;
    }

    @Override
    protected void init() {
        imgList.clear();
        imageLoaderUtil = new ImageLoaderUtil();
        //将轮播的图片放在photos 那里
        //imgList.add("https://w.wallhaven.cc/full/4l/wallhaven-4lkeyr.jpg");
        //imgList.add("https://img-blog.csdnimg.cn/img_convert/8dc4978a27ba3ccdaa697c2d7814f3ac.png");
        imgList.add("https://img-blog.csdnimg.cn/img_convert/52f5dd5a02ab958c9a2b4daa825925e5.png");
        imgList.add("https://longvideo.heytapimage.com/1654072137642_1080x1920_m3.jpg");
        imgList.add("https://longvideo.heytapimage.com/202206061500000001.png");
        imgList.add("https://longvideo.heytapimage.com/1654598843117_1080x1920.jpg");
        mbanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);  //CIRCLE_INDICATOR
        //设置图片加载器
        mbanner.setImageLoader(imageLoaderUtil);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mbanner.setBannerAnimation(Transformer.CubeOut);
        //设置轮播间隔时间
        mbanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        mbanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mbanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        mbanner.setImages(imgList)
                //开始调用的方法，启动轮播图。
                .start();
        dataBeanList = new ArrayList<>();
        recycleViewAdapter = new RecycleViewAdapter(dataBeanList);
        rvRegisterList.setAdapter(recycleViewAdapter);
        upLoadPresenter = new UpLoadPersenterImp(new UpLoadModelImp(), this);
        upLoadPresenter.showFileList(getContext(),currentPager,pageSize);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            recycleViewState = 1;
            currentPager = 1;
            upLoadPresenter.showFileList(getContext(),currentPager,pageSize);
        });

        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            recycleViewState = -1;
            currentPager +=1;
            if (dataBeanList.size() < totalSize) {
                upLoadPresenter.showFileList(getContext(),currentPager,pageSize);
            } else {
                smartRefreshLayout.finishLoadMore(1000);
                smartRefreshLayout.setNoMoreData(true);
            }
        });
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void upLoadSuccess(String data) {

    }

    @Override
    public void upLoadFail(String data) {

    }

    @Override
    public void showFileSuccess(String data) {
        Gson gson = new Gson();
        FileBean fileBean = gson.fromJson(data, FileBean.class);
        totalSize = fileBean.getTotalSize();
        List<FileBean.DataBean> dataBeans = fileBean.getData();
        if (recycleViewState == 1) {
            smartRefreshLayout.finishRefresh();
            if (dataBeans.size() == 0) return;
            for (int i = 0; i < dataBeans.size(); i++) {
                dataBeanList.set(i,dataBeans.get(i));
            }
        } else if (recycleViewState == -1) {
            smartRefreshLayout.finishLoadMore();
            if (dataBeans.size() == 0) return;
            dataBeanList.addAll(dataBeans);
        } else {
            if (dataBeans.size() == 0) return;
            dataBeanList.addAll(dataBeans);
        }
        recycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFileFail(String data) {
        if (recycleViewState == 1) {
            smartRefreshLayout.finishRefresh();
        } else if (recycleViewState == -1){
            smartRefreshLayout.finishLoadMore();
        }
        recycleViewAdapter.notifyDataSetChanged();
    }
}

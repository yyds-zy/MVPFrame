package com.free.fileupload.ui.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.free.fileupload.R;
import com.free.fileupload.ui.BaseActivity;
import com.free.fileupload.ui.BaseFragment;
import com.free.fileupload.ui.fragment.DiscoveryFragment;
import com.free.fileupload.ui.fragment.HomeFragment;
import com.free.fileupload.ui.fragment.MeFragment;
import com.free.fileupload.ui.fragment.MessageFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.tab_home_img)
    ImageView tabHomeImg;
    @BindView(R.id.tab_home_tv)
    TextView tabHomeTv;
    @BindView(R.id.tab_home_bg)
    RelativeLayout tabHomeBg;
    @BindView(R.id.tab_msg_img)
    ImageView tabMsgImg;
    @BindView(R.id.tab_msg_tv)
    TextView tabMsgTv;
    @BindView(R.id.tab_msg_bg)
    RelativeLayout tabMsgBg;
    @BindView(R.id.tab_discovery_img)
    ImageView tabDiscoveryImg;
    @BindView(R.id.tab_discovery_tv)
    TextView tabDiscoveryTv;
    @BindView(R.id.tab_discovery_bg)
    RelativeLayout tabDiscoveryBg;
    @BindView(R.id.tab_me_img)
    ImageView tabMeImg;
    @BindView(R.id.tab_me_tv)
    TextView tabMeTv;
    @BindView(R.id.tab_me_bg)
    RelativeLayout tabMeBg;
    HomeFragment homeFragment;
    MessageFragment messageFragment;
    DiscoveryFragment discoveryFragment;
    MeFragment meFragment;

    BaseFragment mCurFragment;
    RelativeLayout mCurTabIconBg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setToolBar() {
        toolbarTitle.setText(R.string.my_computer);
    }

    @Override
    protected void init() {
        homeFragment = new HomeFragment();
        mCurTabIconBg = tabHomeBg;
        mCurFragment = homeFragment;
        setTabCheckState(mCurTabIconBg,true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_layout,homeFragment);
        transaction.commit();
    }

    @OnClick({R.id.tab_home_bg, R.id.tab_msg_bg, R.id.tab_discovery_bg, R.id.tab_me_bg})
    public void onViewClicked(View view) {
        if (mCurTabIconBg == view) return;
        setTabCheckState(mCurTabIconBg,false);
        setTabCheckState((RelativeLayout) view,true);
        mCurTabIconBg = (RelativeLayout) view;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mCurFragment);
        switch (view.getId()) {
            case R.id.tab_home_bg:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_layout,homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                mCurFragment = homeFragment;
                break;
            case R.id.tab_msg_bg:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.fragment_layout,messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                mCurFragment = messageFragment;
                break;
            case R.id.tab_discovery_bg:
                if (discoveryFragment == null) {
                    discoveryFragment = new DiscoveryFragment();
                    transaction.add(R.id.fragment_layout,discoveryFragment);
                } else {
                    transaction.show(discoveryFragment);
                }
                mCurFragment = discoveryFragment;
                break;
            case R.id.tab_me_bg:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fragment_layout,meFragment);
                } else {
                    transaction.show(meFragment);
                }
                mCurFragment = meFragment;
                break;
        }
        transaction.commit();
    }

    private void setTabCheckState(RelativeLayout view,boolean checked){
        switch (view.getId()) {
            case R.id.tab_home_bg:
                tabHomeImg.setImageResource(checked ? R.mipmap.tab_home_checked : R.mipmap.tab_home_uncheck);
                tabHomeTv.setTypeface(checked ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            case R.id.tab_msg_bg:
                tabMsgImg.setImageResource(checked ? R.mipmap.tab_msg_checked : R.mipmap.tab_msg_uncheck);
                tabMsgTv.setTypeface(checked ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            case R.id.tab_discovery_bg:
                tabDiscoveryImg.setImageResource(checked ? R.mipmap.tab_discovery_checked : R.mipmap.tab_discovery_uncheck);
                tabDiscoveryTv.setTypeface(checked ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
            case R.id.tab_me_bg:
                tabMeImg.setImageResource(checked ? R.mipmap.tab_me_checked : R.mipmap.tab_me_uncheck);
                tabMeTv.setTypeface(checked ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                break;
        }
    }
}

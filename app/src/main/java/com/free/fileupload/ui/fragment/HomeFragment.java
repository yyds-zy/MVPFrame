package com.free.fileupload.ui.fragment;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.free.fileupload.R;
import com.free.fileupload.ui.BaseFragment;
import com.free.fileupload.ui.adapter.FragmentAdapter;
import com.free.fileupload.ui.fragment.home.FileFragment;
import com.free.fileupload.ui.fragment.home.LogFragment;
import com.free.fileupload.ui.fragment.home.MusicFragment;
import com.free.fileupload.ui.fragment.home.PictureFragment;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_tabLayout)
    TabLayout homeTabLayout;
    @BindView(R.id.home_viewPager)
    ViewPager homeViewPager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> tabStrings = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_layout_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void init() {
        tabStrings.add("日志");
        tabStrings.add("图片");
        tabStrings.add("音乐");
        tabStrings.add("文件");
        for (String tab:tabStrings) {
            homeTabLayout.addTab(homeTabLayout.newTab().setText(tab));
        }
        homeTabLayout.setTabTextColors(getResources().getColor(R.color.colorGray, null),
                getResources().getColor(R.color.colorBlue, null));
        homeTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorTransparent, null));
        //homeTabLayout.setTabIndicatorFullWidth(false);  //下划线是否与文字对齐
        //homeTabLayout.setSelectedTabIndicatorHeight(20); //下划线高度
        fragments.add(new LogFragment());
        fragments.add(new PictureFragment());
        fragments.add(new MusicFragment());
        fragments.add(new FileFragment());
        homeViewPager.setOffscreenPageLimit(0);
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), homeTabLayout.getTabCount(), tabStrings, fragments);


        homeViewPager.setAdapter(fragmentAdapter);
        homeViewPager.setCurrentItem(0);
        homeTabLayout.setupWithViewPager(homeViewPager, false);
    }

//    private void setTab(){
//        for (int i = 0; i < tabStrings.size(); i++) {
//            //获取每一个tab对象
//            TabLayout.Tab tabAt = homeTabLayout.getTabAt(i);
//            //将每一个条目设置我们自定义的视图
//            tabAt.setCustomView(R.layout.tab_item);
//            //默认选中第一个
//            if (i == 0) {
//                TextView tabTv = tabAt.getCustomView().findViewById(R.id.tv_tab);
//                tabTv.setTextSize(25);
//                tabTv.setSelected(true);
//            }
//            //通过tab对象找到自定义视图的ID
//            TextView textView = tabAt.getCustomView().findViewById(R.id.tv_tab);
//            textView.setText(tabStrings.get(i));//设置tab上的文字
//        }
//        setTabListener();
//    }

//    private void setTabListener(){
//        homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab);
//                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);//直接用setTextSize(22)也一样
//                textView.setAlpha(0.9f);//透明度
//                textView.invalidate();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab);
//                textView.setTextSize(10);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
}

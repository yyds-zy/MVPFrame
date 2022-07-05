package com.free.fileupload.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTabTitleList;
    private List<Fragment> mFragmentLists;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior, List<String> tabTitleList,List<Fragment> fragmentList) {
        super(fm, behavior);
        mTabTitleList = tabTitleList;
        mFragmentLists = fragmentList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleList.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLists.size();
    }
}

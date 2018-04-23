package com.hzxiaojietan.base.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaojie.tan on 2017/10/26
 * BaseFragmentAdapter
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public BaseFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments,  List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles != null && !mTitles.isEmpty()){
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }
}

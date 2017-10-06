package com.music.maowo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.music.maowo.fragment.HomeFragment;
import com.music.maowo.fragment.CategoryFragment;
import com.music.maowo.fragment.MineFragment4;

/**
 * Created by Jay on 2015/8/31 0031.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private HomeFragment myFragment1 = null;
    private CategoryFragment myFragment2 = null;
    private MineFragment4 mineFragment4 = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new HomeFragment();
        myFragment2 = new CategoryFragment();
        mineFragment4 = new MineFragment4();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = myFragment1;
                break;
            case 1:
                fragment = myFragment2;
                break;
            case 2:
                fragment = mineFragment4;
                break;
        }
        return fragment;
    }


}


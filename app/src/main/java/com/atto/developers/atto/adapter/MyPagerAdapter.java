package com.atto.developers.atto.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atto.developers.atto.fragment.AttoFragment;
import com.atto.developers.atto.fragment.MakerFragment;
import com.atto.developers.atto.fragment.RealTimeTradeFragment;

/**
 * Created by goodn on 2016-08-29.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public static final int PAGE_COUNT = 3;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AttoFragment.newInstance();
            case 1:
                return RealTimeTradeFragment.newInstance();
            case 2:
                return MakerFragment.newInstance();
            default:
                return AttoFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "tab" + position;
    }
}
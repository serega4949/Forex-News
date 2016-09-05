package com.balinasoft.forexnews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.balinasoft.forexnews.fragments.AnalyticsFragment;
import com.balinasoft.forexnews.fragments.LiveNewsFragment;

/**
 * Created by Serega on 06.08.2016.
 */
public class NewsPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public NewsPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LiveNewsFragment();
            case 1:
                return new AnalyticsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];

    }
}

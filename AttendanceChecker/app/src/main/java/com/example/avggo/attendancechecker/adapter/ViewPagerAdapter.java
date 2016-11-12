package com.example.avggo.attendancechecker.adapter;

/**
 * Created by avggo on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.avggo.attendancechecker.model.Filter;
import com.example.avggo.attendancechecker.ui.AttendanceFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[];
    int NumbOfTabs;
    Filter filter;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Filter filter) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.filter = filter;
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            AttendanceFragment al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 1) {
            AttendanceFragment al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 2) {
            AttendanceFragment al = AttendanceFragment.newInstance(filter);
            return al;
        } else return null;
    }

    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    public int getCount() {
        return NumbOfTabs;
    }
}

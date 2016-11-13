package com.example.avggo.attendancechecker.adapter;

/**
 * Created by avggo on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

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
        //Log.i("tagg", "ViewPagerAdapter.getItem()   -- called");
        if (position == 0) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position ZERO called");
            filter.setDone(false);
            AttendanceFragment al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 1) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position ONE called");
            filter.setDone(true);
            AttendanceFragment al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 2) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position TWO called");
            filter.setDone(true);
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

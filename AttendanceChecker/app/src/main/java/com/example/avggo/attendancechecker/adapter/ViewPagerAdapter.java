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

import static com.example.avggo.attendancechecker.MainActivity.DONE_TAB;
import static com.example.avggo.attendancechecker.MainActivity.SUBMITTED_TAB;
import static com.example.avggo.attendancechecker.MainActivity.UNDONE_TAB;
import static com.example.avggo.attendancechecker.MainActivity.submitted;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[];
    int NumbOfTabs;
    Filter filter;

    private AttendanceFragment al;
    private int currentTab;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Filter filter) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.filter = filter;
        this.currentTab = 0;
    }

    public Fragment getItem(int position) {
        //Log.i("tagg", "ViewPagerAdapter.getItem()   -- called");
        if (position == 0) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position ZERO called");
            filter.setDone(false);
            filter.setSubmitted(submitted);
             al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 1) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position ONE called");
            filter.setDone(true);
            filter.setSubmitted(submitted);
             al = AttendanceFragment.newInstance(filter);
            return al;
        } else if (position == 2) {
            Log.i("tagg", "ViewPagerAdapter.getItem()   -- position TWO called");
            filter.setDone(true);
            filter.setSubmitted(!submitted);
            Filter temp = new Filter();
            temp.setDone(filter.getDone());
            temp.setSubmitted(filter.getSubmitted());
            temp.setBuilding("NULL");
            temp.setBuilding(filter.getBuilding());
            temp.setRID(filter.getRID());
            al = AttendanceFragment.newInstance(filter);
            return al;
        } else return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    public int getCount() {
        return NumbOfTabs;
    }
}

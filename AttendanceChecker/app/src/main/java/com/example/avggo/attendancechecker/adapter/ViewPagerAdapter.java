package com.example.avggo.attendancechecker.adapter;

/**
 * Created by avggo on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.avggo.attendancechecker.ui.AttendanceList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[];
    int NumbOfTabs;
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb){
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }
    public Fragment getItem(int position){
        if(position == 0){
            AttendanceList al = new AttendanceList();
            return al;
        }
        else if(position == 1){
            AttendanceList al = new AttendanceList();
            return al;
        }
        else if(position == 2){
            AttendanceList al = new AttendanceList();
            return al;
        }
        else return null;
    }
    public CharSequence getPageTitle(int position){
        return Titles[position];
    }
    public int getCount(){
        return NumbOfTabs;
    }
}

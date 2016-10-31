package com.example.avggo.attendancechecker.adapter;

/**
 * Created by avggo on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.avggo.attendancechecker.ui.AttendanceFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[];
    int NumbOfTabs;
    String RID, building;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, String RID, String building){
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.RID = RID;
        this.building = building;
    }
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, String RID){
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.RID = RID;
        this.building = "NULL";
    }

    public Fragment getItem(int position){
        if(position == 0){
            AttendanceFragment al = AttendanceFragment.newInstance(RID, building);
            return al;
        }
        else if(position == 1){
            AttendanceFragment al = AttendanceFragment.newInstance(RID, building);
            return al;
        }
        else if(position == 2){
            AttendanceFragment al = AttendanceFragment.newInstance(RID, building);
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

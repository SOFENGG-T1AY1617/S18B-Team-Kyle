package com.example.avggo.attendancechecker.adapter;

/**
 * Created by avggo on 10/23/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.example.avggo.attendancechecker.ui.AttendanceFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[];
    int NumbOfTabs;
    String RID;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, String RID){
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.RID = RID;
    }

    public Fragment getItem(int position){
        if(position == 0){
            AttendanceFragment al = new AttendanceFragment();
            al.setRID(RID);
            return al;
        }
        else if(position == 1){
            AttendanceFragment al = AttendanceFragment.newInstance(RID);
            al.setRID(RID);
            return al;
        }
        else if(position == 2){
            AttendanceFragment al = AttendanceFragment.newInstance(RID);
            al.setRID(RID);
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

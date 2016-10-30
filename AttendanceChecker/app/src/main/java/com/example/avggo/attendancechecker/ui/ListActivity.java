package com.example.avggo.attendancechecker.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.ListItem;
import com.example.avggo.attendancechecker.model.TutorialData;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AttendanceAdapter.ItemClickCallback, TabLayout.OnTabSelectedListener{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    DatabaseOpenHelper db;

    private RecyclerView recView;
    private AttendanceAdapter adapter;
    private ArrayList listData;

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;
    private DrawerLayout navDrawer;

    //TABS
    private TabLayout tabLayout;
    private ViewPager pager;
    private static final String[] pageTitles = {"Movies", "Music", "Podcasts", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db = new DatabaseOpenHelper(getBaseContext());

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TABS
        //getSupportActionBar().setElevation(0f);
        //tabLayout = (TabLayout)findViewById(R.id.tbl_main_content);
        //pager = (ViewPager)findViewById(R.id.vpg_main_content);
       // setUpPagerAndTabs();

        listData = (ArrayList) TutorialData.getListData();

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AttendanceAdapter(TutorialData.getListData(), this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        //setUpNavDrawer();
        Bundle extras = getIntent().getBundleExtra("EXTRAS");
    }

    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);

        Intent i = new Intent(this, DetailActivity.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getTitle());
        extras.putString(EXTRA_ATTR, item.getSubTitle());

        i.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(i);
    }

    @Override
    public void onSecondaryClick(int p) {
        ListItem item = (ListItem) listData.get(p);
        //update our data
        if (item.isFavourite()){
            item.setFavourite(false);
        } else {
            item.setFavourite(true);
        }
        //pass new data to adapter and update
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }

    private void initializeNavHeader(){

    }

    /*private void setUpNavDrawer() {
        navDrawer = (DrawerLayout) findViewById(R.id.nvd_act_main);
        navView = (NavigationView)findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                navDrawer.closeDrawers();
                onNavigationItemClick(item.getItemId());
                return true;
            }
        });

        toggle = new ActionBarDrawerToggle(
                this,
                navDrawer,
                toolbar,  /
                R.string.open,
                R.string.close
        ) {


            public void onDrawerClosed(View drawer) {
                super.onDrawerClosed(drawer);
                invalidateOptionsMenu();
            }


            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                invalidateOptionsMenu();
            }
        };

        navDrawer.addDrawerListener(toggle);
    }*/


    //NAVIGATION METHODS
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNavigationItemClick(int id){

    }

    //TAB LISTENERS
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }




}

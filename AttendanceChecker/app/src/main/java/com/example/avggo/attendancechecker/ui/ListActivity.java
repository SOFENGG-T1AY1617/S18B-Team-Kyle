package com.example.avggo.attendancechecker.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.model.ListItem;
import com.example.avggo.attendancechecker.model.TutorialData;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AttendanceAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView;
    private AttendanceAdapter adapter;
    private ArrayList listData;

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;
    private DrawerLayout navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listData = (ArrayList) TutorialData.getListData();

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AttendanceAdapter(TutorialData.getListData(), this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        setUpNavDrawer();
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

    private void setUpNavDrawer() {
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
                this,                  /* host Activity */
                navDrawer,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.open,  /* "open drawer" description */
                R.string.close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View drawer) {
                super.onDrawerClosed(drawer);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                invalidateOptionsMenu();
            }
        };
        navDrawer.addDrawerListener(toggle);
    }

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
}

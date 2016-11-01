package com.example.avggo.attendancechecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.example.avggo.attendancechecker.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabSlider;
    private CharSequence tabList[] = {"current", "done", "submitted"};
    public static final int TAB_NUMBERS = 3;

    //navigation items
    String TITLES[] = {"Help", "Logout"};
    int ICONS[] = {R.drawable.ic_help_black_24dp, R.drawable.ic_input_black_24dp};
    //header
    String NAME;
    String EMAIL;
    int PROFILE = R.drawable.dummy_pic;

    NavigationView mNavigationView;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    private String RID;
    private DatabaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RID = getIntent().getStringExtra("RID");
        //Toast.makeText(getBaseContext(), RID, Toast.LENGTH_LONG).show();

        //SET NAVIGATION TEXT
        NAME = getIntent().getStringExtra("DISPLAY_NAME");
        EMAIL = getIntent().getStringExtra("EMAIL");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Attendance");
        setSupportActionBar(toolbar);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, TAB_NUMBERS, RID);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new AccordionTransformer());
        viewPager.setCurrentItem(0);
        tabSlider = (SlidingTabLayout) findViewById(R.id.tabs);
        tabSlider.setDistributeEvenly(true);
        tabSlider.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabSlider.setViewPager(viewPager);

        db = new DatabaseOpenHelper(getBaseContext());

        //initialize drawer
        initializeDrawer();

        setMenuCounter(R.id.nav_allbuildings, db.getAssignedAttendance(RID, "NULL").size());
        setMenuCounter(R.id.nav_gokongwei, db.getAssignedAttendance(RID, "Gokongwei").size());
        setMenuCounter(R.id.nav_lasallehall, db.getAssignedAttendance(RID, "La Salle Hall").size());
        setMenuCounter(R.id.nav_yuchengco, db.getAssignedAttendance(RID, "Yuchengco").size());
        setMenuCounter(R.id.nav_saintjoseph, db.getAssignedAttendance(RID, "Saint Joseph").size());
        setMenuCounter(R.id.nav_velasco, db.getAssignedAttendance(RID, "Velasco").size());
        setMenuCounter(R.id.nav_miguel, db.getAssignedAttendance(RID, "Miguel").size());
        setMenuCounter(R.id.nav_andrew, db.getAssignedAttendance(RID, "Andrew").size());
        setMenuCounter(R.id.nav_razon, db.getAssignedAttendance(RID, "Razon").size());
    }

    private void filterByBuilding(String building) {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, TAB_NUMBERS, RID, building);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new AccordionTransformer());
        viewPager.setCurrentItem(0);
        tabSlider = (SlidingTabLayout) findViewById(R.id.tabs);
        tabSlider.setDistributeEvenly(true);
        tabSlider.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabSlider.setViewPager(viewPager);
    }

    public void initializeDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                if (menuItem.getTitle().equals("Log Out")) {
                    goToLogin();
                } else if (menuItem.getTitle().equals("All Buildings")) {
                    filterByBuilding("NULL");
                } else if (menuItem.getTitle().equals("Gokongwei")) {
                    filterByBuilding("Gokongwei");
                } else if (menuItem.getTitle().equals("Andrew")) {
                    filterByBuilding("Andrew");
                }
                ((DrawerLayout) findViewById(R.id.DrawerLayout)).closeDrawers();
                return true;
            }
        });

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(NAME);
                TextView email = (TextView) findViewById(R.id.email);
                email.setText(EMAIL);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };

        Drawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }

    private void setMenuCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) mNavigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

    public void goToLogin() {
        Intent loginscreen = new Intent(this, LoginActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginscreen);
        this.finish();
    }
}

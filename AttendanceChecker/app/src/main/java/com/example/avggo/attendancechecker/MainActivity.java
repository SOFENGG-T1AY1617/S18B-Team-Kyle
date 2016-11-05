package com.example.avggo.attendancechecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.example.avggo.attendancechecker.adapter.ViewPagerAdapter;
import com.example.avggo.attendancechecker.ui.HelpActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabSlider;
    private CharSequence tabList[] = {"current", "done", "submitted"};
    private ArrayList<String> buildings = new ArrayList<String>();
    ArrayList<String> curBuildings = new ArrayList<String>();
    public static final int TAB_NUMBERS = 3;
    public static final int DONE_TAB = 1;

    //navigation items
    String TITLES[] = {"Help", "Logout"};
    int ICONS[] = {R.drawable.ic_help_black_24dp, R.drawable.ic_input_black_24dp};
    //header
    String NAME;
    String EMAIL;
    int PROFILE = R.drawable.dummy_pic;

    NavigationView mNavigationView;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    Button submitButton;


    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    private String RID;
    private DatabaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RID = getIntent().getStringExtra("RID");
        populateAllBuildings();
        //Toast.makeText(getApplicationContext(), RID, Toast.LENGTH_LONG).show();

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
        submitButton = (Button) findViewById(R.id.submitSheetButton);
        submitButton.setVisibility(View.GONE);
        submitButton.setEnabled(false);
        tabSlider = (SlidingTabLayout) findViewById(R.id.tabs);
        tabSlider.setDistributeEvenly(true);
        tabSlider.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });
        tabSlider.setViewPager(viewPager);
        db = new DatabaseOpenHelper(getBaseContext());

        curBuildings = db.getAssignedBuildings(RID);
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

        tabSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                if(position == DONE_TAB) {
                    submitButton.setVisibility(View.VISIBLE);
                    if (db.getAssignedAttendance(RID, "NULL").size() == 0)
                        submitButton.setEnabled(true);
                    else
                        submitButton.setEnabled(false);
                }else
                    submitButton.setVisibility(View.GONE);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    private void populateAllBuildings(){
        buildings.add("All Buildings");
        buildings.add("La Salle Hall");
        buildings.add("Yuchengco");
        buildings.add("Saint Joseph");
        buildings.add("Velasco");
        buildings.add("Miguel");
        buildings.add("Gokongwei");
        buildings.add("STRC");
        buildings.add("Andrew");
        buildings.add("Razon");
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
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });
        tabSlider.setViewPager(viewPager);
    }

    public void initializeDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        for(int i = 1; i < mNavigationView.getMenu().size() - 2; i++){
            mNavigationView.getMenu().getItem(i).setVisible(false);
        }

        for(int i = 0; i < curBuildings.size(); i++){
            mNavigationView.getMenu().getItem(buildings.indexOf(curBuildings.get(i))).setVisible(true);
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                switch(menuItem.getTitle().toString()) {
                    case "Log Out":
                        goToLogin();
                        break;
                    case "All Buildings":
                        filterByBuilding("NULL");
                        break;
                    case "Gokongwei":
                        filterByBuilding("Gokongwei");
                        break;
                    case "Andrew":
                        filterByBuilding("Andrew");
                        break;
                    case "Help":
                        Intent help = new Intent();
                        help.setClass(getBaseContext(), HelpActivity.class);
                        startActivity(help);
                        break;
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
                //TODO Code here will execute once drawer is closed
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

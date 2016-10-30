package com.example.avggo.attendancechecker;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
<<<<<<< HEAD

=======
>>>>>>> e5da41edc56ccfd09139fc6a6b8dbab19adb3e52

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RID = getIntent().getStringExtra("RID");
        Toast.makeText(getBaseContext(), RID, Toast.LENGTH_LONG).show();

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

        //initialize drawer
        initializeDrawer();
    }

    public void initializeDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
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
}

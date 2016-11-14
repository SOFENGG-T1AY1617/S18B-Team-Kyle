package com.example.avggo.attendancechecker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.adapter.ViewPagerAdapter;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.Filter;
import com.example.avggo.attendancechecker.ui.HelpActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    public static boolean submitted = false;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabSlider;
    private CharSequence tabList[] = {"current", "done", "submitted"};
    private ArrayList<String> buildings = new ArrayList<String>();
    ArrayList<String> curBuildings = new ArrayList<String>();
    ArrayList<Integer> buldingIDs = new ArrayList<Integer>();
    ArrayList<Attendance> listData;
    public static final int TAB_NUMBERS = 3;

    public static final int UNDONE_TAB = 0;
    public static final int DONE_TAB = 1;
    public static final int SUBMITTED_TAB = 2;
    int currentHourFilter;
    int currentMinuteFilter;


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
    public DatabaseOpenHelper db;
    Timer timer;
    Filter mainFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RID = getIntent().getStringExtra("RID");

        mainFilter = new Filter();
        mainFilter.setBuilding("NULL");
        mainFilter.setRID(RID);

        populateAllBuildings();
        //Toast.makeText(getApplicationContext(), RID, Toast.LENGTH_LONG).show();

        Filter f = new Filter();
        f.setRID(RID);
        f.setBuilding("NULL");

        //SET NAVIGATION TEXT
        NAME = getIntent().getStringExtra("DISPLAY_NAME");
        EMAIL = getIntent().getStringExtra("EMAIL");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Attendance");
        setSupportActionBar(toolbar);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, TAB_NUMBERS, f);
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

        tabSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

                if(position == DONE_TAB) {

                    submitButton.setVisibility(View.VISIBLE);
                    mainFilter.setTab(DONE_TAB);
                    mainFilter.setDone(true);
                    mainFilter.setSubmitted(false);
                    pagerAdapter.setFilter(mainFilter);
                    pagerAdapter.getItem(1);
                    viewPager.setCurrentItem(1);
                    //filter(mainFilter);
                    if (db.getAssignedAttendance(mainFilter).size() == 0)
                        submitButton.setEnabled(true);
                    else
                        submitButton.setEnabled(false);
                }else if(position == SUBMITTED_TAB){
                    mainFilter.setSubmitted(true);
                    mainFilter.setDone(true);
                    mainFilter.setTab(SUBMITTED_TAB);
                    //filter(mainFilter);
                }
                else if (position == UNDONE_TAB){
                    mainFilter.setDone(false);
                    mainFilter.setSubmitted(false);
                    mainFilter.setTab(UNDONE_TAB);
                    //filter(mainFilter);
                    submitButton.setVisibility(View.GONE);
                }

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitted = true;
                //pagerAdapter.removeAll();
            }
        });

        new MainTask().execute();


        /*Calendar c = GregorianCalendar.getInstance();

        for(int i = 0; i < lisData.size(); i++) {
            int hour = getHour(lisData.get(i));
            int minute = getMinute(lisData.get(i));
            int hourNow = c.get(Calendar.HOUR_OF_DAY);
            int minuteNow = c.get(Calendar.MINUTE);

            if (hour > hourNow || (hour == hourNow && minute > minuteNow))
                scheduleTask(getBaseContext(), hour, minute);
        }*/
    }

    public int getStartHour(Attendance a){
        if(a.getStartTime().charAt(0) == '0')
            return Integer.parseInt(a.getStartTime().substring(1, 2));

        return Integer.parseInt(a.getStartTime().substring(0, 2));
    }

    public int getStartMinute(Attendance a){
        if(a.getStartTime().charAt(3) == '0')
            return Integer.parseInt(a.getStartTime().substring(4));

        return Integer.parseInt(a.getStartTime().substring(3));
    }
    public int getEndHour(Attendance a){
        if(a.getEndTime().charAt(0) == '0')
            return Integer.parseInt(a.getEndTime().substring(1, 2));

        return Integer.parseInt(a.getEndTime().substring(0, 2));
    }
    public int getEndMinute(Attendance a){
        if(a.getEndTime().charAt(3) == '0')
            return Integer.parseInt(a.getEndTime().substring(4));

        return Integer.parseInt(a.getEndTime().substring(3));
    }

    private void populateAllBuildings(){
        buildings.add("All Buildings");
        buildings.add("La Salle Hall");
        buildings.add("Yuchengco");
        buildings.add("Saint Joseph");
        buildings.add("Velasco");
        buildings.add("Miguel");
        buildings.add("Gokongwei");
        buildings.add("Andrew");
        buildings.add("Razon");

        buldingIDs.add(R.id.nav_allbuildings);
        buldingIDs.add(R.id.nav_lasallehall);
        buldingIDs.add(R.id.nav_yuchengco);
        buldingIDs.add(R.id.nav_saintjoseph);
        buldingIDs.add(R.id.nav_velasco);
        buldingIDs.add(R.id.nav_miguel);
        buldingIDs.add(R.id.nav_gokongwei);
        buldingIDs.add(R.id.nav_andrew);
        buldingIDs.add(R.id.nav_razon);
    }

    public void filter(Filter filter) {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, TAB_NUMBERS, filter);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new AccordionTransformer());
        viewPager.setCurrentItem(filter.getTab());
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

        for(int i = 1; i < buildings.size(); i++){
            mNavigationView.getMenu().getItem(i).setVisible(false);
        }

        for(int i = 0; i < curBuildings.size(); i++){
            mNavigationView.getMenu().getItem(buildings.indexOf(curBuildings.get(i))).setVisible(true);
            if(i == 0)
                setMenuCounter(buldingIDs.get(buildings.indexOf(curBuildings.get(i))), db.getAssignedAttendance(mainFilter).size());
            else {
                mainFilter.setBuilding(curBuildings.get(i));
                setMenuCounter(buldingIDs.get(buildings.indexOf(curBuildings.get(i))), db.getAssignedAttendance(mainFilter).size());
            }
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                switch(menuItem.getItemId()) {
                    case R.id.nav_logout:
                        timer.cancel();
                        goToLogin();
                        break;
                    case R.id.nav_allbuildings:
                        mainFilter.setBuilding("NULL");
                        filter(mainFilter);
                        break;
                    case R.id.nav_gokongwei:
                        mainFilter.setBuilding("Gokongwei");
                        filter(mainFilter);
                        break;
                    case R.id.nav_andrew:
                        mainFilter.setBuilding("Andrew");
                        filter(mainFilter);
                        break;
                    case R.id.nav_help:
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

    private void scheduleTask(Context c){

        //Calendar today = Calendar.getInstance();
        //today.set(Calendar.HOUR_OF_DAY, hour);
        //today.set(Calendar.MINUTE, minute);
        //today.set(Calendar.SECOND, 0);

        //Now create the time and schedule it
        timer = new Timer();

        //Use this if you want to execute it once
        timer.scheduleAtFixedRate(new AttendanceScheduler(c), 0, 1000);
    }

    private class AttendanceScheduler extends TimerTask
    {
        private Context c;

        public AttendanceScheduler(Context c){
            this.c = c;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(listData.size() > 0) {
                        Calendar c = GregorianCalendar.getInstance();
                        int startHour = getStartHour(listData.get(0));
                        int startMinute = getStartMinute(listData.get(0));
                        int endHour = getEndHour(listData.get(0));
                        int endMinute = getEndMinute(listData.get(0));
                        int hourNow = c.get(Calendar.HOUR_OF_DAY);
                        int minuteNow = c.get(Calendar.MINUTE);

                        Log.i("YES" + listData.size(), "Time Now: " + hourNow + ":" + minuteNow + " Start Time: " + startHour + ":" + startMinute + " End Time: " + endHour + ":" + endMinute);

                        if ((startHour == hourNow && minuteNow >= startMinute) || (((hourNow > startHour) && (hourNow < endHour)) || (hourNow == endHour && minuteNow <= endMinute))) {
                            currentHourFilter = startHour;
                            currentMinuteFilter = startMinute;
                            mainFilter.setStartHour(currentHourFilter);
                            mainFilter.setStartMinute(currentMinuteFilter);
                            showDialog(startHour, startMinute);
                            listData.remove(0);
                            Log.i("REMOVED", "UPDATED");
                        }
                        else if(startHour < hourNow && endHour < hourNow || (hourNow == endHour && minuteNow > endMinute)) {
                            listData.remove(0);
                            Log.i("REMOVED", "EXCEEDED");
                        }
                    }
                    else
                        timer.cancel();
                }
            });
        }
    }

    public void showDialog(int startHour, int startMinute){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Scheduler");
        String h = String.format("%02d", startHour);
        String m = String.format("%02d", startMinute);

        adb.setMessage("The next classes (" + h + ":" + m + ") are about to start. Click \"OK\" to proceed and the list will be filtered.");
        adb.setCancelable(false);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                filter(mainFilter);
                mNavigationView.getMenu().performIdentifierAction(R.id.nav_allbuildings, 0);
            }
        });

        adb.show();
    }

    class MainTask extends AsyncTask<Object, Void, String> {

        MainTask() {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {
            Filter initialFilter = new Filter();
            initialFilter.setBuilding("NULL");
            initialFilter.setRID(RID);
            initialFilter.setStatus("unique");
            listData = db.getAssignedAttendance(initialFilter);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            scheduleTask(getBaseContext());
        }
    }
}

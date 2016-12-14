package com.example.avggo.attendancechecker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.example.avggo.attendancechecker.adapter.ViewPagerAdapter;
import com.example.avggo.attendancechecker.meneger.AccountManager;
import com.example.avggo.attendancechecker.meneger.AttendanceDateManager;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.Filter;
import com.example.avggo.attendancechecker.ui.AttendanceFragment;
import com.example.avggo.attendancechecker.ui.HelpActivity;
import com.example.avggo.attendancechecker.ui.ReportUCActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static Boolean submitted;
    Boolean notified = false;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabSlider;
    private CharSequence tabList[] = {"pending", "done"};
    private ArrayList<String> buildings = new ArrayList<String>();
    ArrayList<String> curBuildings = new ArrayList<String>();
    ArrayList<Integer> buldingIDs = new ArrayList<Integer>();
    ArrayList<Attendance> listData;
    public static final int TAB_NUMBERS = 2;

    public static final int UNDONE_TAB = 0;
    Boolean huh = false;
    public static final int DONE_TAB = 1;
    public static final int SUBMITTED_TAB = 2;
    int currentHourFilter;
    int currentMinuteFilter;
    int curHour;
    int curMinute;
    int lastMinute;
    int lastHour;
    int verylastMinute;
    int verylastHour;
    int hourNow;
    int minuteNow;
    int startHour, startMinute;

    //header
    String NAME;
    String EMAIL;

    NavigationView mNavigationView;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    Button submitButton;
    TextView emptyView;

    Filter MainActivityFilter;

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    private String RID;
    private Boolean exceedPopped;
    Boolean alerted2 = false;
    Boolean alerted = false;
    Boolean timerCanceled = false;
    Boolean earlyAlerted = false;
    Boolean initializedFirstTime = false;
    int first_hour;
    int first_minute;
    public DatabaseOpenHelper db;
    Timer timer;
    Filter mainFilter;
    String day;

    AttendanceFragment undoneFragment, doneFragment, submittedFragment;

    @Override
    protected void onPause(){
        super.onPause();
        AccountManager.account.getSubmitManager().saveState(this);
        //AttendanceDateManager.saveState(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        AccountManager.account.getSubmitManager().saveState(this);
        //AttendanceDateManager.saveState(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        AccountManager.account.getSubmitManager().resumeState(this);
        //AttendanceDateManager.resumeState(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        submitted = AccountManager.account.getSubmitManager().isSubmittedDate(sdf.format(Calendar.getInstance().getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        day = dayFormat.format(calendar.getTime());

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
        viewPager.setCurrentItem(0);
        submitButton = (Button) findViewById(R.id.submitSheetButton);
        emptyView = (TextView) findViewById(R.id.empty_view);
        submitButton.setVisibility(View.GONE);
        submitButton.setBackgroundColor(Color.GRAY);
        submitButton.setEnabled(false);
        submitButton.setStateListAnimator(null);
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

        AccountManager.account.getSubmitManager().resumeState(this);

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        // Get the value for the run counter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        submitted = AccountManager.account.getSubmitManager().isSubmittedDate(sdf.format(Calendar.getInstance().getTime()));

        if(!submitted) {
            //timerCanceled = true;
            new MainTask().execute();
        }

        tabSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                //refresh();

                if(position == DONE_TAB) {
                    if(emptyView != null)
                        emptyView.setText("You haven't checked any classes yet.");
                    submitButton.setVisibility(View.VISIBLE);
                    mainFilter.setDone(false);
                    mainFilter.setSubmitted(false);
                    mainFilter.setTab(DONE_TAB);
                    Filter temp = new Filter();
                    temp.setDone(mainFilter.getDone());
                    temp.setSubmitted(mainFilter.getSubmitted());
                    temp.setBuilding("NULL");
                    temp.setRID(mainFilter.getRID());
                    temp.setStartMinute(-1);
                    temp.setStartMinute(-1);

                    int size = db.getAssignedAttendance(temp).size();
                    Log.i("tagg", "--------------------------------------------------------------"+
                            "size is " + size + " " +!submitButton.getText().equals("ALREADY SUBMITTED"));
                    if (size == 0 && !submitButton.getText().equals("ALREADY SUBMITTED") || submitted || timerCanceled){
                        temp.setDone(true);
                        Integer size2 = db.getAssignedAttendance(temp).size();

                        String weekDay;
                        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

                        Calendar calendar = Calendar.getInstance();
                        weekDay = dayFormat.format(calendar.getTime());

                        weekDay = weekDay.substring(0, 1);

                        if(weekDay.equals(("S"))){
                            submitButton.setText("NO LIST FOUND");
                            submitButton.setBackgroundColor(Color.GRAY);
                            submitButton.setEnabled(false);
                        }
                        else if(size2 > 0 && !submitted || (!submitted && timerCanceled)){
                            Log.i("WEEENT IIN", size2.toString());
                            Log.i("WEEENT IIN", timerCanceled.toString());
                            Log.i("WEEENT IIN", submitted.toString());
                            submitButton.setEnabled(true);
                            submitButton.setText("SUBMIT");
                            submitButton.setBackgroundColor(Color.rgb(8, 120, 48));
                        }
                        else if(submitted){
                            submitButton.setEnabled(false);
                            submitButton.setText("ALREADY SUBMITTED");
                            submitButton.setBackgroundColor(Color.GRAY);
                        }
                    }
                    else
                        submitButton.setEnabled(false);

                    Log.i("tagg", "tabSlider.pageListener()  done");
                    //filter(mainFilter);
                    //Log.i("tagg", "tabSlider.pageListener()  filtered already");
                    //if(timerCanceled) {
                        mainFilter.setStartMinute(-1);
                        mainFilter.setStartHour(-1);
                        mainFilter.setDone(true);
                        Log.i("DONE", "DONE");


                    if(submitted){
                        mainFilter.setTab(SUBMITTED_TAB);
                    }

                }/*else if(position == SUBMITTED_TAB){

                    mainFilter.setTab(SUBMITTED_TAB);
                    submitButton.setVisibility(View.GONE);
                }*/
                else if (position == UNDONE_TAB){
                    if(emptyView != null)
                        emptyView.setText("You have checked all classes.");
                    mainFilter.setStartHour(currentHourFilter);
                    mainFilter.setStartMinute(currentMinuteFilter);
                    mainFilter.setTab(UNDONE_TAB);
                    mainFilter.setDone(false);
                    submitButton.setVisibility(View.GONE);
                }
                else {
                    submitButton.setVisibility(View.GONE);
                }

                pagerAdapter.notifyDataSetChanged();

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
                confirmSubmission();
            }
        });

        initializeTodayDate();
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

    public void initializeTodayDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(Calendar.getInstance().getTime());

        AccountManager.account.getDateManager().resumeState(getBaseContext());
        if (!AccountManager.account.getDateManager().isAttendanceDate(date)) {
            AccountManager.account.getDateManager().addAttendanceDate(date);
            String query;
            query = "INSERT INTO Attendance " +
                    "(courseoffering_id) " +
                    "SELECT co.id " +
                    "from CourseOffering co inner join Room r on co.room_id = r.id " +
                    "inner join RotationRoom rr on r.id = rr.room_id;";

            db.getReadableDatabase().execSQL(query);

            query = "UPDATE Attendance SET date = '" + date + "' WHERE date IS NULL;";

            db.getReadableDatabase().execSQL(query);
        }
        AccountManager.account.getDateManager().saveState(getBaseContext());
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
                        if(timer != null)
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
                    case R.id.nav_lasallehall:
                        mainFilter.setBuilding("La Salle Hall");
                        filter(mainFilter);
                        break;
                    case R.id.nav_miguel:
                        mainFilter.setBuilding("Miguel");
                        filter(mainFilter);
                        break;
                    case R.id.nav_razon:
                        mainFilter.setBuilding("Razon");
                        filter(mainFilter);
                        break;
                    case R.id.nav_saintjoseph:
                        mainFilter.setBuilding("Saint Joseph");
                        filter(mainFilter);
                        break;
                    case R.id.nav_yuchengco:
                        mainFilter.setBuilding("Yuchengco");
                        filter(mainFilter);
                        break;
                    case R.id.nav_velasco:
                        mainFilter.setBuilding("Velasco");
                        filter(mainFilter);
                        break;
                    case R.id.nav_help:
                        Intent help = new Intent();
                        help.setClass(getBaseContext(), HelpActivity.class);
                        startActivity(help);
                        break;
                    case R.id.report_uc:
                        Intent report = new Intent();
                        report.setClass(getBaseContext(), ReportUCActivity.class);
                        startActivity(report);
                        break;
                }
                pagerAdapter.notifyDataSetChanged();

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
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                    //Log.i("taggs", "current day: " + dayFormat.format(Calendar.getInstance().getTime()));

                    if(!day.equals(dayFormat.format(Calendar.getInstance().getTime()))){
                        day = dayFormat.format(Calendar.getInstance().getTime());
                        submitted = false;
                        Filter initialFilter = new Filter();
                        initialFilter.setBuilding("NULL");
                        initialFilter.setRID(RID);
                        initialFilter.setStatus("unique");
                        listData = db.getAssignedAttendance(initialFilter);
                        verylastHour = getEndHour(listData.get(listData.size() - 1));
                        verylastMinute = getEndMinute(listData.get(listData.size() - 1));
                    }
                    if(listData.size() > 0) {
                        Calendar c = GregorianCalendar.getInstance();
                        startHour = getStartHour(listData.get(0));
                        startMinute = getStartMinute(listData.get(0));
                        int endHour = getEndHour(listData.get(0));
                        int endMinute = getEndMinute(listData.get(0));
                        hourNow = c.get(Calendar.HOUR_OF_DAY);
                        minuteNow = c.get(Calendar.MINUTE);
                        if(!initializedFirstTime) {
                            initializedFirstTime = true;
                            first_hour = startHour;
                            first_minute = startMinute;
                        }

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
                           // Toast.makeText(getApplicationContext(), "Exceeded " + listData.get(0).getCoursecode(), Toast.LENGTH_SHORT).show();
                            //listData.get(0).setCode("Checker Error");
                            //db.updateAttendance(listData.get(0));
                            listData.remove(0);
                            if(!alerted){
                                alerted = true;
                                alertMissedAttendance();
                            }
                            if(listData.size() > 0) {
                                if(listData.size() == 1){
                                    lastMinute = getStartMinute(listData.get(0));
                                    lastHour = getStartHour(listData.get(0));
                                    verylastMinute = getEndMinute(listData.get(0));
                                    verylastHour = getEndHour(listData.get(0));
                                    Log.i("VERYLAST", verylastHour + ":" + verylastMinute);
                                }
                                mainFilter.setStartHour(getStartHour(listData.get(0)));
                                mainFilter.setStartMinute(getStartMinute(listData.get(0)));
                                filter(mainFilter);
                                mNavigationView.getMenu().performIdentifierAction(R.id.nav_allbuildings, 0);
                            }
                            else {
                                mainFilter.setDone(true);
                                mainFilter.setStartMinute(1000);
                                mainFilter.setStartHour(1000);
                                filter(mainFilter);
                                mNavigationView.getMenu().performIdentifierAction(R.id.nav_allbuildings, 0);
                            }
                            //filter(mainFilter);
                            Log.i("REMOVED", "EXCEEDED");
                        }
                        else {
                            if(!notified) {
                                notified = true;
                                pagerAdapter.notifyDataSetChanged();
                            }
                            Log.i("EARLY", "TOO EARLY");
                            if(!earlyAlerted && (hourNow < first_hour || hourNow == first_hour && minuteNow < first_minute)){
                                alertTooEarly();
                                earlyAlerted = true;
                                mainFilter.setStartMinute(1000);
                                mainFilter.setStartHour(1000);
                                filter(mainFilter);
                            }
                        }
                    }
                    else {
                        Log.i("tagg", "TIMER");
                        timerCanceled = true;
                        if(!huh) {
                            if (hourNow > verylastHour || hourNow == verylastHour && minuteNow > verylastMinute) {
                                /*huh = true;
                                submitted = true;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String date = sdf.format(Calendar.getInstance().getTime());
                                Log.i("DATE", date);


                                SebmetMeneger.submitToDate(date);
                                submitButton.setText("ALREADY SUBMITTED");
                                pagerAdapter.notifyDataSetChanged();
                                submitButton.setEnabled(false);
                                mainFilter.setStartHour(-1);
                                mainFilter.setStartMinute(-1);
                                pagerAdapter.notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "You have missed all classes. Your attendance sheet has been successfully saved.", Toast.LENGTH_SHORT).show();*/
                            }
                        }
                        //timer.cancel();
                    }
                }
            });
        }
    }

    public void alertMissedAttendance(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Alert");
        adb.setMessage("You have missed several classes and they are automatically removed from the list.");
        adb.setCancelable(false);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        });

        adb.show();
    }

    public void alertTooEarly(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Alert");
        adb.setMessage("You are too early. There are no available classes at the moment.");
        adb.setCancelable(false);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });

        adb.show();
    }

    public void confirmSubmission(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Confirmation");
        adb.setMessage("Are you sure you want to submit?").setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.setCancelable(true);

        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                submitted = true;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(Calendar.getInstance().getTime());
                Log.i("DATE", date);
                AccountManager.account.getSubmitManager().submitToDate(date);
                submitButton.setText("ALREADY SUBMITTED");
                pagerAdapter.notifyDataSetChanged();
                submitButton.setEnabled(false);
                mainFilter.setStartHour(-1);
                mainFilter.setStartMinute(-1);
                pagerAdapter.notifyDataSetChanged();
                submitButton.setBackgroundColor(Color.GRAY);
                Toast.makeText(getBaseContext(), "Your attendance sheet has been successfully saved.", Toast.LENGTH_SHORT).show();
            }
        });

        adb.show();
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

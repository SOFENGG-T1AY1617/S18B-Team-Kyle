package com.example.avggo.attendancechecker.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.MainActivity;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.Filter;
import com.example.avggo.attendancechecker.support.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by avggo on 10/23/2016.
 */

public class AttendanceFragment extends android.support.v4.app.Fragment implements AttendanceAdapter.ItemClickCallback {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private static final int ATTENDANCE_FRAGMENT_REQUEST = 1;
    private static int most_recent_item = -1;

    private static int tabID;
    public static boolean loaded = false;

    RecyclerViewEmptySupport recView;
    AttendanceAdapter adapter;
    TextView emptyView;
    ArrayList listData;
    DatabaseOpenHelper db;
    LayoutInflater li;
    ViewGroup c;
    Bundle b;
    Filter f;

    public static AttendanceFragment newInstance(Filter filter) {
        AttendanceFragment f = new AttendanceFragment();

        Bundle args = new Bundle();

        args.putString("RID", filter.getRID());
        args.putString("BUILDING", filter.getBuilding());
        args.putInt("START_H", filter.getStartHour());
        args.putInt("START_M", filter.getStartMinute());
        args.putBoolean("ISDONE", filter.getDone());
        args.putBoolean("ISSUBMITTED", filter.getSubmitted());
        args.putInt("TAB_ID", filter.getTab());
        f.setArguments(args);

        tabID = filter.getTab();

        return (f);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list, container, false);

        this.li = inflater;
        this. c = container;
        this. b = savedInstanceState;

        //Toast.makeText(v.getContext(), getRID(), Toast.LENGTH_LONG).show();
        db = new DatabaseOpenHelper(getContext());

        new AttendanceFragmentTask(getContext(), v, this).execute();


        return v;
    }

    @Override
    public void onItemClick(int p) {
        Attendance item = (Attendance) listData.get(p);
        most_recent_item = p;

        Intent i = new Intent(getActivity(), DetailActivity.class);

        i.putExtra("ATTENDANCE_ITEM",item);

        Log.i("tagg", "AttendanceFragment.onItemClick()  item clicked has id " + item.getId());

        startActivityForResult(i, ATTENDANCE_FRAGMENT_REQUEST);
    }

    @Override
    public void onSecondaryClick(int p) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        db = new DatabaseOpenHelper(getContext());
        // Check which request we're responding to
        if (requestCode == ATTENDANCE_FRAGMENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Attendance item = (Attendance) data.getSerializableExtra("CODED_ATTENDANCE_ITEM");
                db.updateAttendance(item);
                db.updateAttendanceRemark(item);
                //listData.remove(most_recent_item);
                //recView.removeViewAt(most_recent_item);
                adapter.setListData(listData);
                Attendance i = (Attendance) listData.get(most_recent_item);
                if(i.getCode() == null)
                    adapter.removeItem(most_recent_item);
                else {
                    listData.set(most_recent_item, item);
                    //adapter.setListData(listData);
                    adapter.updateItem(most_recent_item, item);
                    //adapter.notifyItemChanged(most_recent_item, i);

                }
                //onCreateView(li, c, b);
                //adapter.notifyItemRemoved(most_recent_item);
                //adapter.notifyItemRangeChanged(most_recent_item, listData.size());
                //getLoaderManager().restartLoader(0, null, this);
                //adapter.notifyDataSetChanged();
                Log.i("tagg", "AttendanceFragment.onActivityResult()   db updated on id " + item.getId());
                Log.i("tagg", "AttendanceFragment.onActivityResult()   list size is " + listData.size());
                // Do something with the contact here (bigger example below)
            }
        }
    }

    String getRID() {
        return (getArguments().getString("RID"));
    }

    String getBuilding() {
        return (getArguments().getString("BUILDING"));
    }

    int getStartHour() {
        return (getArguments().getInt("START_H"));
    }

    int getStartMinute() {
        return (getArguments().getInt("START_M"));
    }

    boolean getDone(){ return (getArguments().getBoolean("ISDONE"));}

    boolean getSubmitted() { return (getArguments().getBoolean("ISSUBMITTED"));}

    int getTab(){ return (getArguments().getInt("TAB_ID"));}

    public class AttendanceFragmentTask extends AsyncTask<Object, Void, String> {
        Context context;
        View v;
        AttendanceAdapter.ItemClickCallback it;
        ProgressDialog pd;

        AttendanceFragmentTask(Context context, View v, AttendanceAdapter.ItemClickCallback it) {
            this.context = context;
            this.v = v;
            this.it = it;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... params) {

            f = new Filter();
            f.setBuilding(getBuilding());
            f.setRID(getRID());
            f.setStartHour(getStartHour());
            f.setStartMinute(getStartMinute());
            f.setDone(getDone());
            f.setSubmitted(getSubmitted());

            Log.i("tagg", "AttendanceFragment.onCreateView " + f.getDone());

            listData = (ArrayList) db.getAssignedAttendance(f);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            recView = (RecyclerViewEmptySupport) v.findViewById(R.id.rec_list);
            recView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recView.setEmptyView(v.findViewById(R.id.empty_view));
            if(getActivity() != null) {
                adapter = new AttendanceAdapter(db.getAssignedAttendance(f), getActivity());
                recView.setAdapter(adapter);
                adapter.setItemClickCallback(it);
            }
        }
    }

    public List<Attendance> getAttendanceList(){
        return this.listData;
    }
}

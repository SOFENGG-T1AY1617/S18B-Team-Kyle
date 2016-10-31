package com.example.avggo.attendancechecker.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.ListItem;
import com.example.avggo.attendancechecker.model.TutorialData;

import java.util.ArrayList;

/**
 * Created by avggo on 10/23/2016.
 */

public class AttendanceFragment extends android.support.v4.app.Fragment implements AttendanceAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView;
    private AttendanceAdapter adapter;
    private ArrayList listData;
    private DatabaseOpenHelper db;
    private String RID;

    public static AttendanceFragment newInstance(String RID, String building) {
        AttendanceFragment f = new AttendanceFragment();

        Bundle args = new Bundle();

        args.putString("RID", RID);
        args.putString("BUILDING", building);
        f.setArguments(args);

        return(f);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list, container, false);

        //Toast.makeText(v.getContext(), getRID(), Toast.LENGTH_LONG).show();

        db = new DatabaseOpenHelper(getContext());

        listData = (ArrayList) db.getAssignedAttendance(getRID(), getBuilding());

        recView = (RecyclerView) v.findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AttendanceAdapter(db.getAssignedAttendance(getRID(), getBuilding()), getActivity());
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        return v;
    }

    @Override
    public void onItemClick(int p) {
        Attendance item = (Attendance) listData.get(p);

        Intent i = new Intent(getActivity(), DetailActivity.class);

        i.putExtra("PIC", item.getPic());
        i.putExtra("FNAME", item.getFname());
        i.putExtra("COLLEGE", item.getCollege());
        i.putExtra("COURSE_C", item.getCoursecode());
        i.putExtra("COURSE_N", item.getCoursename());
        i.putExtra("TIME", item.getStartTime() + " - " + item.getEndTime());
        i.putExtra("ROOM_N", item.getRoom());

        startActivity(i);
    }

    @Override
    public void onSecondaryClick(int p) {

    }

    public void setRID(String RID){
        this.RID = RID;
    }

    String getRID() {
        return(getArguments().getString("RID"));
    }
    String getBuilding() {
        return(getArguments().getString("BUILDING"));
    }
}

package com.example.avggo.attendancechecker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.adapter.AttendanceAdapter;
import com.example.avggo.attendancechecker.model.ListItem;
import com.example.avggo.attendancechecker.model.TutorialData;

import java.util.ArrayList;

/**
 * Created by avggo on 10/23/2016.
 */

public class AttendanceList extends android.support.v4.app.Fragment implements AttendanceAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView;
    private AttendanceAdapter adapter;
    private ArrayList listData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list, container, false);

        listData = (ArrayList) TutorialData.getListData();

        recView = (RecyclerView) v.findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AttendanceAdapter(TutorialData.getListData(), getActivity());
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        return v;
    }

    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);

        Intent i = new Intent(getActivity(), DetailActivity.class);

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
}

package com.example.avggo.attendancechecker.model;

import com.example.avggo.attendancechecker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avggo on 10/12/2016.
 */

public class TutorialData {
    private static final String[] titles = {"G202",
            "G203",
            "A909",
            "G201",
            "G204",
            "G206"
    };
    private static final String[] subTitles = {"Ms. Kyle-Althea Santos",
            "Mr. Vince Gonzales",
            "Mr. Junlyn Alburo",
            "Mr. Jarrell Chua",
            "Mr. Winfred Villaluna",
            "Mr. Dhiren Dhanani",
            "Ms. Darlene Marpa"

    };
    private static final int icon = R.drawable.ic_tonality_black_36dp;

    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>(); //change to attendance and

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView

        //create ListItem with dummy data, then add them to our List
        for (int i = 0; i < titles.length; i++) {
            ListItem item = new ListItem();
            item.setTitle(titles[i]);
            item.setSubTitle(subTitles[i]);
            data.add(item);
        }

        return data;
    }
}

package com.example.avggo.attendancechecker.model;

import java.util.ArrayList;

/**
 * Created by Bryan on 12/6/2016.
 */

public class AttendanceDateModel {

    private ArrayList<String> arreyLest = new ArrayList<String>();


    public ArrayList getSubmittedDates() {
        return arreyLest;
    }

    public void addAttendanceDate(String dete) {
        if (!arreyLest.contains(dete)) {
            arreyLest.add(dete);
        }
    }

    public boolean hasDate(String dete){
        return arreyLest.contains(dete);
    }
}

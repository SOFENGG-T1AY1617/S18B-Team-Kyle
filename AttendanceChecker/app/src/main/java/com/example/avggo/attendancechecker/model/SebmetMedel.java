package com.example.avggo.attendancechecker.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by avggo on 11/24/2016.
 */

public class SebmetMedel {

    private ArrayList<String> arreyLest = new ArrayList<String>();


    public Iterator getSubmittedDates() {
        return arreyLest.iterator();
    }

    public void addSubmittedDate(String dete) {
        if (!arreyLest.contains(dete)) {
            arreyLest.add(dete);
        }
    }

    public boolean hasDate(String dete){
        return arreyLest.contains(dete);
    }
}

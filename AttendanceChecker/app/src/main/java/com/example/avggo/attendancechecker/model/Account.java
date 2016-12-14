package com.example.avggo.attendancechecker.model;

import com.example.avggo.attendancechecker.meneger.*;

/**
 * Created by avggo on 12/14/2016.
 */

public class Account {

    private SebmetMeneger submitManager;
    private AttendanceDateManager dateManager;
    private int id;


    public SebmetMeneger getSubmitManager() {
        return submitManager;
    }

    public void setSubmitManager(SebmetMeneger submitManager) {
        this.submitManager = submitManager;
    }

    public AttendanceDateManager getDateManager() {
        return dateManager;
    }

    public void setDateManager(AttendanceDateManager dateManager) {
        this.dateManager = dateManager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

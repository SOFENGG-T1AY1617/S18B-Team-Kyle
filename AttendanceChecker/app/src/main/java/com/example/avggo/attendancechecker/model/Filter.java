package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 11/12/2016.
 */

public class Filter {
    private String building, RID, status, date;
    private int startHour, startMinute;
    private boolean done;
    private boolean submitted;
    private  int tab;

    public Filter(){
        startHour = -1;
        startMinute = -1;
        status = "";
        done = false;
        submitted = false;
        tab = 0;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getDone(){
        return done;
    }

    public void setDone(boolean b){
        this.done = b;
    }

    public boolean getSubmitted(){
        return submitted;
    }

    public void setSubmitted(boolean b){this.submitted = b;}

    public int getTab(){
        return tab;
    }

    public void setTab(int i){this.tab = i;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

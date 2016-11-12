package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 11/12/2016.
 */

public class Filter {
    private String building, RID;
    private int startHour, startMinute;

    public Filter(){
        startHour = -1;
        startMinute = -1;
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
}

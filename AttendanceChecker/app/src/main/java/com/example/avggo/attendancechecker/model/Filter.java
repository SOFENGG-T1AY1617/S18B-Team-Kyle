package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 11/12/2016.
 */

public class Filter {
    private String building, RID, status;
    private int startHour, startMinute;
    private boolean done;

    public Filter(){
        startHour = -1;
        startMinute = -1;
<<<<<<< HEAD
        status = "";
=======
        done = false;
>>>>>>> fcb309469345346c5fd608fb64d64e0338b743de
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

<<<<<<< HEAD
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
=======
    public boolean getDone(){
        return done;
    }

    public void setDone(boolean b){
        this.done = b;
>>>>>>> fcb309469345346c5fd608fb64d64e0338b743de
    }
}

package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 11/30/2016.
 */

public class UnscheduledClass {
    private String roomName;
    private String faculty;
    private String remarks;

    public UnscheduledClass(){
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

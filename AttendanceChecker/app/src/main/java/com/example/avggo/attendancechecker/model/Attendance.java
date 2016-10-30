package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 10/10/2016.
 */

public class Attendance {
    public static final String TABLE_NAME = "Attendance";
    public static final String COL_ID = "id";
    public static final String COL_ROOM = "room";
    public static final String COL_COID = "courseoffering_id";
    public static final String COL_FACULTYID = "faculty_id";
    public static final String COL_A_STATUS = "status_id";
    public static final String COL_REMARKS = "remarks";
    public static final String COL_DATE = "date";
    public static final String COL_TIME_SET = "time_set";

    private String room, coursecode, coursename, startTime, endTime, fname, code, email, remarks;
    private byte[] pic;

    public Attendance(){}
    public Attendance(String room, String coursecode, String coursename, String startTime, String endTime, String fname, String code, String email, String remarks, byte[] pic){
        this.room = room;
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fname = fname;
        this.code = code;
        this.email = email;
        this. remarks = remarks;
        this.pic = pic;
    }

    public String getRoom() {
        return room;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public String getFname() {
        return fname;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getRemarks() {
        return remarks;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

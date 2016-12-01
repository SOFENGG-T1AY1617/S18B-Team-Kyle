package com.example.avggo.attendancechecker.model;

import java.io.Serializable;

/**
 * Created by avggo on 10/10/2016.
 */

public class Attendance implements Serializable{
    public static final String TABLE_NAME = "Attendance";
    public static final String COL_ID = "id";
    public static final String COL_COID = "courseoffering_id";
    public static final String COL_FACULTYID = "faculty_id";
    public static final String COL_A_STATUS = "status_id";
    public static final String COL_REMARKS = "remarks";
    public static final String COL_DATE = "date";
    public static final String COL_TIME_SET = "time_set";


    private int id;
    private String room;
    private String coursecode;
    private String coursename;
    private String startTime;
    private String endTime;
    private String fname;
    private String code;
    private String email;
    private String remarks;
    private String college;
    private String reason;
    private String subName;
    private String new_start_time;
    private String new_end_time;
    private String new_room;
    private byte[] subPic;
    private byte[] pic;

    public Attendance() {
    }

    public Attendance(String room, String coursecode, String coursename, String startTime, String endTime, String fname, String code, String email, String remarks, byte[] pic) {
        this.room = room;
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fname = fname;
        this.code = code;
        this.email = email;
        this.remarks = remarks;
        this.pic = pic;
    }

    public String toString(){
        return "id: " +id+" name: "+fname+" coursecode: "+coursecode+" startTime: "+startTime+" endTime: "+endTime+" room: "
                +room+" code: "+code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNew_start_time() {
        return new_start_time;
    }

    public void setNew_start_time(String new_start_time) {
        this.new_start_time = new_start_time;
    }

    public String getNew_end_time() {
        return new_end_time;
    }

    public void setNew_end_time(String new_end_time) {
        this.new_end_time = new_end_time;
    }

    public String getNew_room() {
        return new_room;
    }

    public void setNew_room(String new_room) {
        this.new_room = new_room;
    }

    public byte[] getSubPic() {
        return subPic;
    }

    public void setSubPic(byte[] subPic) {
        this.subPic = subPic;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}

package com.example.avggo.attendancechecker.model;

import java.sql.Blob;

/**
 * Created by avggo on 10/9/2016.
 */

public class Faculty {
    public static final String TABLE_NAME = "Faculty";
    public static final String COL_ID = "id";
    public static final String COL_FNAME = "first_name";
    public static final String COL_MNAME = "middle_name";
    public static final String COL_LNAME = "last_name";
    public static final String COL_COLLEGE = "college";
    public static final String COL_EMAIL = "email";
    public static final String COL_PIC = "pic";
    public static final String COL_MOBNUM = "mobile_number";
    public static final String COL_DEPT = "department";

    private String fname, mname, lname, college, email, mobnum, dept;
    private byte[] image;

    public Faculty(){}
    public Faculty(String fname, String mname, String lname, String college, String email, String mobnum, String dept){
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.college = college;
        this.email = email;
        this.mobnum = mobnum;
        this.dept = dept;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public String getCollege() {
        return college;
    }

    public String getEmail() {
        return email;
    }

    public String getMobnum() {
        return mobnum;
    }

    public String getDept() {
        return dept;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobnum(String mobnum) {
        this.mobnum = mobnum;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

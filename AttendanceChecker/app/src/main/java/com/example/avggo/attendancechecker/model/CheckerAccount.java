package com.example.avggo.attendancechecker.model;

/**
 * Created by avggo on 10/10/2016.
 */

public class CheckerAccount {
    public static final String TABLE_NAME = "CheckerAccount";
    public static final String COL_ID = "id";
    public static final String COL_FNAME = "first_name";
    public static final String COL_MNAME = "middle_name";
    public static final String COL_LNAME = "last_name";
    public static final String COL_UN = "user_name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PW = "password";
    public static final String COL_RID = "rotation_id";

    private String fname, mname, lname, un, email, pw;
    private int checkerid, rid;

    public CheckerAccount(){}
    public CheckerAccount(int checkerid, String fname, String mname, String lname, String un, String email, String pw, int rid){
        this.checkerid = checkerid;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.un = un;
        this.email = email;
        this.pw = pw;
        this. rid = rid;
    }

    public int getCheckerid() {
        return checkerid;
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

    public String getUn() {
        return un;
    }

    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    public int getRid() {
        return rid;
    }

    public void setCheckerid(int checkerid) {
        this.checkerid = checkerid;
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

    public void setUn(String un) {
        this.un = un;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}

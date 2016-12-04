package com.example.avggo.attendancechecker.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private String fname, mname, lname, college;
    private Bitmap pic;

    public Faculty() {
    }

    public Faculty(String fname, String mname, String lname, String college, byte[] pic) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.college = college;
        this.pic = BitmapFactory.decodeByteArray(pic, 0, pic.length);
    }

    public String getFullName() {
        return fname + " " + mname + " " + lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = BitmapFactory.decodeByteArray(pic, 0, pic.length);
    }

    @Override
    public boolean equals(Object o) {
        return this.fname == ((Faculty) o).fname &&
                this.mname == ((Faculty) o).mname &&
                this.lname == ((Faculty) o).lname;
    }
}

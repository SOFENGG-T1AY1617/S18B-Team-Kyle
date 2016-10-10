package com.example.avggo.attendancechecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by avggo on 10/9/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "attendance_checker";

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, SCHEMA, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Faculty.TABLE_NAME  + " ("
                + Faculty.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Faculty.COL_FNAME + " TEXT, "
                + Faculty.COL_MNAME + " TEXT, "
                + Faculty.COL_LNAME + " TEXT, "
                + Faculty.COL_COLLEGE + " TEXT, "
                + Faculty.COL_EMAIL + " TEXT, "
                + Faculty.COL_MOBNUM + " TEXT, "
                + Faculty.COL_DEPT + " TEXT, "
                + Faculty.COL_PIC + " BLOB);";
        db.execSQL(sql);
        sql = "CREATE TABLE Course ("
                + "courseid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "coursecode TEXT, "
                + "coursename TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE CourseOffering ("
                + "co_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "courseid INTEGER, "
                + "time TEXT, "
                + "section TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE " + Attendance.TABLE_NAME  + " ("
                + Attendance.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Attendance.COL_ROOM + " TEXT, "
                + Attendance.COL_COID + " INTEGER, "
                + Attendance.COL_FACULTYID + " INTEGER, "
                + Attendance.COL_CODE + " TEXT, "
                + Attendance.COL_REMARKS + " TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE AttendanceCode ("
                + "ac_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "code TEXT, "
                + "name TEXT, "
                + "description TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE CheckerAccount ("
                + "checkerid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "c_firstname TEXT, "
                + "c_middlename TEXT, "
                + "c_lastname TEXT, "
                + "username TEXT, "
                + "email TEXT, "
                + "password TEXT, "
                + "rotationid INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE RotationRoom ("
                + "rotationid INTEGER"
                + "roomid INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Room ("
                + "roomname INTEGER"
                + "roomid INTEGER"
                + "buildingid INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Rotation ("
                + "rotationid TEXT PRIMARY KEY);";
        db.execSQL(sql);
        sql = "CREATE TABLE Building ("
                + "buildingid INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "buildingname INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

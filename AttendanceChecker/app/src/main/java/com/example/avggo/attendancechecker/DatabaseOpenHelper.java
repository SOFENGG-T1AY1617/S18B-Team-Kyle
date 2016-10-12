package com.example.avggo.attendancechecker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.CheckerAccount;
import com.example.avggo.attendancechecker.model.Faculty;

/**
 * Created by avggo on 10/9/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "attendance_checker";

    public DatabaseOpenHelper(Context context) {
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
        sql = "CREATE TABLE " + CheckerAccount.TABLE_NAME  + " ("
                + CheckerAccount.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CheckerAccount.COL_FNAME + " TEXT, "
                + CheckerAccount.COL_MNAME + " TEXT, "
                + CheckerAccount.COL_LNAME + " TEXT, "
                + CheckerAccount.COL_UN + " TEXT, "
                + CheckerAccount.COL_EMAIL + " TEXT, "
                + CheckerAccount.COL_PW + " TEXT, "
                + CheckerAccount.COL_RID + " INTEGER);";
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

        sql = "INSERT INTO CheckerAccount (\"c_firstname\", \"c_middlename\", \"c_lastname\", \"username\", \"email\", \"password\", \"rotationid\" ) VALUES ('Vince', 'Gornal', 'Gonzales', 'test', 'test@gmail.com', 'test', 'A');" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public CheckerAccount checkIfUserExists(String username) {
        CheckerAccount u = new CheckerAccount();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(CheckerAccount.TABLE_NAME, null, " "+ CheckerAccount.COL_UN + "=? ", new String[]{username}, null, null, null);

        if (c.moveToFirst()) {
            u.setCheckerid(c.getInt(c.getColumnIndex(CheckerAccount.COL_ID)));
            u.setFname(c.getString(c.getColumnIndex(CheckerAccount.COL_FNAME)));
            u.setMname(c.getString(c.getColumnIndex(CheckerAccount.COL_MNAME)));
            u.setLname(c.getString(c.getColumnIndex(CheckerAccount.COL_LNAME)));
            u.setUn(c.getString(c.getColumnIndex(CheckerAccount.COL_UN)));
            u.setEmail(c.getString(c.getColumnIndex(CheckerAccount.COL_EMAIL)));
            u.setPw(c.getString(c.getColumnIndex(CheckerAccount.COL_PW)));
            u.setRid(c.getInt(c.getColumnIndex(CheckerAccount.COL_RID)));

        } else {
            u = null;
        }

        c.close();
        db.close();

        return u;
    }
}

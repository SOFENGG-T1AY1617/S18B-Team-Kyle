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
        context.deleteDatabase("attendance_checker");
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
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "code TEXT, "
                + "name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE CourseOffering ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "course_id INTEGER, "
                + "faculty_id INTEGER, "
                + "term_id INTEGER, "
                + "section INTEGER, "
                + "time_start TEXT, "
                + "time_end TEXT, "
                + "room_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE " + Attendance.TABLE_NAME  + " ("
                + Attendance.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Attendance.COL_ROOM + " TEXT, "
                + Attendance.COL_COID + " INTEGER, "
                + Attendance.COL_FACULTYID + " INTEGER, "
                + Attendance.COL_A_STATUS + " INTEGER, "
                + Attendance.COL_DATE + " TEXT, "
                + Attendance.COL_TIME_SET + " TEXT, "
                + Attendance.COL_REMARKS + " TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE AttendanceStatus ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
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
                + "id TEXT"
                + "room_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Room ("
                + "id INTEGER"
                + "name TEXT"
                + "building_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Rotation ("
                + "rotation_id TEXT PRIMARY KEY);";
        db.execSQL(sql);
        sql = "CREATE TABLE Building ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE Term ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "start TEXT"
                + "end TEXT"
                + "term_no INTEGER"
                + "year_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE AcademicYear ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT);";
        db.execSQL(sql);

        sql = "INSERT INTO CheckerAccount (\"first_name\", \"middle_name\", \"last_name\", \"user_name\", \"email\", \"password\", \"rotation_id\" ) VALUES ('Vince', 'Gornal', 'Gonzales', 'test', 'test@gmail.com', 'test', 'A');";
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

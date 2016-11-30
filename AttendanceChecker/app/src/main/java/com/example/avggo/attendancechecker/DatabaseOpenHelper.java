package com.example.avggo.attendancechecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.avggo.attendancechecker.model.Attendance;
import com.example.avggo.attendancechecker.model.CheckerAccount;
import com.example.avggo.attendancechecker.model.Faculty;
import com.example.avggo.attendancechecker.model.Filter;
import com.example.avggo.attendancechecker.model.UnscheduledClass;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by avggo on 10/9/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    Context context;
    public static final String SCHEMA = "attendance_checker";

    public DatabaseOpenHelper(Context context) {
        super(context, SCHEMA, null, 1);
        //context.deleteDatabase("attendance_checker");
        this.context = context;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabaseName(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Faculty.TABLE_NAME + " ("
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
                + "section TEXT, "
                + "time_start TEXT, "
                + "time_end TEXT, "
                + "days TEXT, "
                + "room_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE " + Attendance.TABLE_NAME + " ("
                + Attendance.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Attendance.COL_COID + " INTEGER DEFAULT NULL, "
                + Attendance.COL_A_STATUS + " INTEGER DEFAULT NULL, "
                + Attendance.COL_DATE + " TEXT DEFAULT NULL, "
                + Attendance.COL_TIME_SET + " TEXT DEFAULT NULL, "
                + Attendance.COL_REMARKS + " TEXT DEFAULT NULL);";
        db.execSQL(sql);
        sql = "CREATE TABLE AttendanceStatus ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "code TEXT, "
                + "name TEXT, "
                + "description TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE " + CheckerAccount.TABLE_NAME + " ("
                + CheckerAccount.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CheckerAccount.COL_FNAME + " TEXT, "
                + CheckerAccount.COL_MNAME + " TEXT, "
                + CheckerAccount.COL_LNAME + " TEXT, "
                + CheckerAccount.COL_UN + " TEXT, "
                + CheckerAccount.COL_EMAIL + " TEXT, "
                + CheckerAccount.COL_PW + " TEXT, "
                + CheckerAccount.COL_RID + " TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE RotationRoom ("
                + "rotation_id INTEGER, "
                + "room_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Room ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "building_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE Rotation ("
                + "id TEXT PRIMARY KEY);";
        db.execSQL(sql);
        sql = "CREATE TABLE Building ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE Term ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "start TEXT, "
                + "end TEXT, "
                + "term_no INTEGER, "
                + "year_id INTEGER);";
        db.execSQL(sql);
        sql = "CREATE TABLE AcademicYear ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT);";
        db.execSQL(sql);
        sql = "CREATE TABLE MakeupClass ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "new_start_time TEXT, "
                + "new_end_time TEXT, "
                + "date TEXT, "
                + "new_rm_id INTEGER, "
                + "status_id INTEGER DEFAULT NULL, "
                + "reason TEXT, "
                + "attendance_id INTEGER, "
                + "sub_id INTEGER, "
                + "remarks TEXT DEFAULT NULL);";
        db.execSQL(sql);
        sql = "CREATE TABLE Substitute ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "faculty_id INTEGER, "
                + "date TEXT DEFAULT NULL);";
        db.execSQL(sql);
        sql = "CREATE TABLE UnscheduledClass ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "room_id INTEGER, "
                + "faculty_id INTEGER, "
                + "remarks TEXT DEFAULT NULL);";
        db.execSQL(sql);

        sql = "INSERT INTO CheckerAccount (\"first_name\", \"middle_name\", \"last_name\", \"user_name\", \"email\", \"password\", \"rotation_id\" ) VALUES ('Vince', 'Gornal', 'Gonzales', 'test', 'test@gmail.com', 'test', 'A');";
        db.execSQL(sql);

        sql = "INSERT INTO CheckerAccount (\"first_name\", \"middle_name\", \"last_name\", \"user_name\", \"email\", \"password\", \"rotation_id\" ) VALUES ('Bryan', 'Huh', 'Alburo', 'test2', 'test2@gmail.com', 'test', 'B');";
        db.execSQL(sql);

        initializeDBData(db);
    }

    public ArrayList<Attendance> getAssignedAttendance(Filter f) {
        String weekDay;
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        weekDay = weekDay.substring(0, 1);

        String isDone = (f.getDone()) ? " not " : "";
        //Log.i("tagg", String.valueOf(f.getDone()));
        String query;

        if (f.getBuilding().equals("NULL")) {
            if(f.getStartHour() != -1) {
                query = "select f.first_name, f.middle_name, f.last_name, f.college, c.code, c.name 'course_name', co.time_start, co.time_end, r.name 'room_name', f.pic, a.remarks, a.id 'id', m.reason, m.date, m.new_start_time, m.new_end_time, (select r2.name from room r2 where r2.id = m.new_rm_id) new_room, (select f1.first_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) sfname, (select f1.middle_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) smname, (select f1.last_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) slname, (select f1.pic from substitute s inner join faculty f1 on s.faculty_id = f1.id) spic, (SELECT ats.name from attendancestatus ats where a.status_id = ats.id) 'acode' \n" +
                        "from attendance a inner join courseoffering co on a.courseoffering_id = co.id \n" +
                        "inner join faculty f on f.id = co.faculty_id \n" +
                        "inner join course c on c.id = co.course_id \n" +
                        "inner join room r on co.room_id = r.id \n" +
                        "inner join rotationroom rr on r.id = rr.room_id \n" +
                        "left join makeupclass m on m.attendance_id = a.id \n" +
                        "where rr.rotation_id = '" + f.getRID() + "' and co.days like '%" + weekDay + "%' and a.status_id is "+ isDone +" null and co.time_start LIKE '%" + f.getStartHour() + ":" + f.getStartMinute() + "%' order by co.time_start;";
            }
            else if(f.getStatus().equals("unique")){
                query = "select co.time_start, co.time_end, f.first_name, f.middle_name, f.last_name, f.college, c.code, a.remarks, a.id 'id', m.reason, m.date, m.new_start_time, m.new_end_time, (select r2.name from room r2 where r2.id = m.new_rm_id) new_room, (select f1.first_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) sfname, (select f1.middle_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) smname, (select f1.last_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) slname, (select f1.pic from substitute s inner join faculty f1 on s.faculty_id = f1.id) spic, (SELECT name from attendancestatus ats where a.status_id = ats.id) 'acode', c.name 'course_name', r.name 'room_name', f.pic " +
                        "from attendance a inner join courseoffering co on a.courseoffering_id = co.id " +
                        "inner join faculty f on f.id = co.faculty_id " +
                        "inner join course c on c.id = co.course_id " +
                        "inner join room r on co.room_id = r.id " +
                        "inner join rotationroom rr on r.id = rr.room_id " +
                        "left join makeupclass m on m.attendance_id = a.id \n" +
                        "where rr.rotation_id = '" + f.getRID() + "' and co.days like '%" + weekDay + "%' and a.status_id is null group by 1 order by co.time_start;";
            }
            else
                query = "select f.first_name, f.middle_name, f.last_name, f.college, c.code, c.name 'course_name', co.time_start, co.time_end, r.name 'room_name', f.pic, a.remarks, a.id 'id', m.reason, m.date, m.new_start_time, m.new_end_time, (select r2.name from room r2 where r2.id = m.new_rm_id) new_room, (select f1.first_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) sfname, (select f1.middle_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) smname, (select f1.last_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) slname, (select f1.pic from substitute s inner join faculty f1 on s.faculty_id = f1.id) spic, (SELECT name from attendancestatus ats where a.status_id = ats.id) 'acode' \n" +
                        "from attendance a inner join courseoffering co on a.courseoffering_id = co.id \n" +
                        "inner join faculty f on f.id = co.faculty_id \n" +
                        "inner join course c on c.id = co.course_id \n" +
                        "inner join room r on co.room_id = r.id \n" +
                        "inner join rotationroom rr on r.id = rr.room_id \n" +
                        "left join makeupclass m on m.attendance_id = a.id \n" +
                        "where rr.rotation_id = '" + f.getRID() + "' and co.days like '%" + weekDay + "%' and a.status_id is "+ isDone +"null order by co.time_start;";
        } else {
            if(f.getStartHour() != -1) {
                query = "select f.first_name, f.middle_name, f.last_name, f.college, c.code, c.name 'course_name', co.time_start, co.time_end, r.name 'room_name', f.pic, b.name 'bname', a.remarks, a.id 'id', m.reason, m.date, m.new_start_time, m.new_end_time, (select r2.name from room r2 where r2.id = m.new_rm_id) new_room, (select f1.first_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) sfname, (select f1.middle_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) smname, (select f1.last_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) slname, (select f1.pic from substitute s inner join faculty f1 on s.faculty_id = f1.id) spic, (SELECT name from attendancestatus ats where a.status_id = ats.id) 'acode' \n" +
                        "from attendance a inner join courseoffering co on a.courseoffering_id = co.id\n" +
                        "inner join faculty f on f.id = co.faculty_id\n" +
                        "inner join course c on c.id = co.course_id\n" +
                        "inner join room r on co.room_id = r.id\n" +
                        "inner join rotationroom rr on r.id = rr.room_id\n" +
                        "inner join building b on r.building_id = b.id\n" +
                        "left join makeupclass m on m.attendance_id = a.id \n" +
                        "where rr.rotation_id = '" + f.getRID() + "' and co.days like '%" + weekDay + "%' and a.status_id is "+ isDone +" null and bname = '" + f.getBuilding() + "' and co.time_start LIKE '%" + f.getStartHour() + ":" + f.getStartMinute() + "%' order by co.time_start;";
            }
            else
                query = "select f.first_name, f.middle_name, f.last_name, f.college, c.code, c.name 'course_name', co.time_start, co.time_end, r.name 'room_name', f.pic, b.name 'bname', a.remarks, a.id 'id', m.reason, m.date, m.new_start_time, m.new_end_time, (select r2.name from room r2 where r2.id = m.new_rm_id) new_room, (select f1.first_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) sfname, (select f1.middle_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) smname, (select f1.last_name from substitute s inner join faculty f1 on s.faculty_id = f1.id) slname, (select f1.pic from substitute s inner join faculty f1 on s.faculty_id = f1.id) spic, (SELECT name from attendancestatus ats where a.status_id = ats.id) 'acode' \n" +
                        "from attendance a inner join courseoffering co on a.courseoffering_id = co.id\n" +
                        "inner join faculty f on f.id = co.faculty_id\n" +
                        "inner join course c on c.id = co.course_id\n" +
                        "inner join room r on co.room_id = r.id\n" +
                        "inner join rotationroom rr on r.id = rr.room_id\n" +
                        "inner join building b on r.building_id = b.id\n" +
                        "left join makeupclass m on m.attendance_id = a.id \n" +
                        "where rr.rotation_id = '" + f.getRID() + "' and co.days like '%" + weekDay + "%' and a.status_id is "+ isDone +" null and bname = '" + f.getBuilding() + "' order by co.time_start;";
        }

        ArrayList<Attendance> assignedAttendance = new ArrayList<>();
        //Log.i("tagg", "DB.getAssignedAttendance query is  "+query+"\n" + f.getSubmitted());

        if(!f.getSubmitted()) {

            Cursor c = db.rawQuery(query, null);


            //Log.i("tagg", "DB.getAssignedAttendance result size is " + assignedAttendance.size());

            if (c.moveToFirst()) {
                while (c.isAfterLast() == false) {
                    String first_name = c.getString(c.getColumnIndex("first_name"));
                    String middle_name = c.getString(c.getColumnIndex("middle_name"));
                    String last_name = c.getString(c.getColumnIndex("last_name"));
                    String college = c.getString(c.getColumnIndex("college"));
                    String code = c.getString(c.getColumnIndex("code"));
                    String course_name = c.getString(c.getColumnIndex("course_name"));
                    String time_start = c.getString(c.getColumnIndex("time_start"));
                    String time_end = c.getString(c.getColumnIndex("time_end"));
                    String room_name = c.getString(c.getColumnIndex("room_name"));
                    byte[] pic = c.getBlob(c.getColumnIndex("pic"));
                    int id = c.getInt(c.getColumnIndex("id"));
                    String aCode = c.getString(c.getColumnIndex("acode"));
                    String remark = c.getString(c.getColumnIndex("remarks"));

                    Attendance a = new Attendance();

                    a.setFname(first_name + " " + middle_name + " " + last_name);
                    a.setCollege(college);
                    a.setCoursecode(code);
                    a.setCoursename(course_name);
                    a.setStartTime(time_start);
                    a.setEndTime(time_end);
                    a.setRoom(room_name);
                    a.setPic(pic);
                    a.setId(id);
                    a.setCode(aCode);
                    a.setRemarks(remark);
                    Log.i("DATE2", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    if(c.getString(c.getColumnIndex("date")) != null && c.getString(c.getColumnIndex("date")).equals(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))) {
                        a.setReason(c.getString(c.getColumnIndex("reason")));
                        a.setNew_start_time(c.getString(c.getColumnIndex("new_start_time")));
                        a.setNew_end_time(c.getString(c.getColumnIndex("new_end_time")));
                        a.setNew_room(c.getString(c.getColumnIndex("new_room")));
                        a.setSubName(c.getString(c.getColumnIndex("sfname")) + " " + c.getString(c.getColumnIndex("smname")) + " " + c.getString(c.getColumnIndex("slname")));
                        a.setSubPic(c.getBlob(c.getColumnIndex("spic")));
                        Log.i("DATE1", c.getString(c.getColumnIndex("date")));
                        Log.i("DATE2", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    }
                    //Log.i("tagg", "DB.getAssignedAttendance() " + a.toString());
                    if(a.getCode() == null || !a.getCode().equals("Checker Error"))
                        assignedAttendance.add(a);

                    c.moveToNext();

                }
            }

            c.close();
            db.close();
        }

        return assignedAttendance;
    }

    public void addUnscheduledClass(UnscheduledClass uc){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT id as 'huh' from room r where r.name LIKE '%" + uc.getRoomName() +"%';";

        Cursor c = db.rawQuery(query, null);
        Log.i("tagg", "DB.updateAttendance() query is " + query);

        int room_id = 1;

        if (c.moveToFirst()) {
            room_id = c.getInt(c.getColumnIndex("huh"));
            Log.i("tagg", room_id + "-id");
        }
        c.close();

        int faculty_id;

        query = "SELECT id as 'huh' from faculty f where f.last_name LIKE '%" + uc.getFaculty() +"%';";

        c = db.rawQuery(query, null);
        Log.i("tagg", "DB.updateAttendance() query is " + query);

        faculty_id = 1;

        if (c.moveToFirst()) {
            faculty_id = c.getInt(c.getColumnIndex("huh"));
            Log.i("tagg", faculty_id + "-id");
        }
        c.close();

        query = "INSERT INTO UnscheduledClass (\"room_id\", \"faculty_id\", \"remarks\") VALUES ('" + room_id + "', '" + faculty_id + "', '" + uc.getRemarks() + "');";
        db.execSQL(query);
    }

    public Boolean roomFound(String roomName){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT id as 'huh' from room r where r.name LIKE '%" + roomName +"%';";

        Cursor c = db.rawQuery(query, null);
        Log.i("tagg", "DB.updateAttendance() query is " + query);

        if (!c.moveToFirst()) {
            return false;
        }
        else
            return true;
    }

    public Boolean facultyFound(String faculty){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT id as 'huh' from faculty f where f.last_name LIKE '%" + faculty +"%';";

        Cursor c = db.rawQuery(query, null);
        Log.i("tagg", "DB.updateAttendance() query is " + query);

        if (!c.moveToFirst()) {
            return false;
        }
        else
            return true;
    }

    public void updateAttendance(Attendance a){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT id as 'huh' from attendancestatus where name LIKE '%" + a.getCode()+"%';";

        Cursor c = db.rawQuery(query, null);
        Log.i("tagg", "DB.updateAttendance() query is " + query);

        int id = 1;

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex("huh"));
            Log.i("tagg", id + "-id");
        }
        c.close();

        query = "UPDATE attendance " +
                "SET status_id =  "+id+" " +
                "WHERE id = "+ a.getId() +";";

        Log.i("tagg", "DB.updateAttendance() query is " + query);

        db.execSQL(query);
        db.close();
    }

    public void updateAttendanceRemark(Attendance a){

        SQLiteDatabase db = getReadableDatabase();

        String query;

        query = "UPDATE attendance " +
                "SET remarks = '" + a.getRemarks() + "' " +
                "WHERE id = "+ a.getId() +";";

        Log.i("tagg", "DB.updateAttendance() query is " + query);

        db.execSQL(query);
        db.close();
    }

    public ArrayList<String> getAssignedBuildings(String RID){
        String weekDay;
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        ArrayList<String> buildings = new ArrayList<String>();

        buildings.add("All Buildings");

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        weekDay = weekDay.substring(0, 1);

        String query;

        query = "select distinct(b.name)\n" +
                "from rotationroom rr inner join room r on rr.room_id = r.id\n" +
                "inner join building b on r.building_id = b.id\n" +
                "inner join courseoffering co on r.id = co.room_id\n" +
                "where days like '%" + weekDay + "%' and rr.rotation_id = '" + RID + "';";

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            while (c.isAfterLast() == false) {
                String building = c.getString(c.getColumnIndex("name"));
                buildings.add(building);
                c.moveToNext();
            }
        }

        c.close();

        return buildings;
    }

    private void initializeDBData(SQLiteDatabase db) {
        String sql;

        sql = "INSERT INTO Course (\"code\", \"name\") VALUES ('ADVANDB', 'Advanced Topics In Database Systems');";
        db.execSQL(sql);
        sql = "INSERT INTO Course (\"code\", \"name\") VALUES ('COMPRO1', 'Introduction To Computer Programming');";
        db.execSQL(sql);
        sql = "INSERT INTO Course (\"code\", \"name\") VALUES ('COMPRO2', 'Advanced C Programming');";
        db.execSQL(sql);
        sql = "INSERT INTO Course (\"code\", \"name\") VALUES ('DISCTRU', 'Discrete Structures');";
        db.execSQL(sql);
        sql = "INSERT INTO Course (\"code\", \"name\") VALUES ('ST-STAT', 'Statistics And Probability For St');";
        db.execSQL(sql);

        sql = "INSERT INTO Term (\"start\", \"end\", \"term_no\", \"year_id\") VALUES ('2016-04-01', '2016-07-01', '1', '1');";
        db.execSQL(sql);

        sql = "INSERT INTO AcademicYear (\"name\") VALUES ('AY 2016 - 2017');";
        db.execSQL(sql);

        sql = "INSERT INTO Building (\"name\") VALUES ('Gokongwei');";
        db.execSQL(sql);
        sql = "INSERT INTO Building (\"name\") VALUES ('Andrew');";
        db.execSQL(sql);

        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G208', '1');"; //1
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G205', '1');"; //2
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G209', '1');"; //3
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G306A', '1');"; //4
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G302A', '1');"; //5
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G302B', '1');"; //6
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G201', '1');"; //7
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G204', '1');"; //8
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G213', '1');"; //9
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('G206', '1');"; //10
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('A1101', '2');"; //11
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('A1102', '2');"; //12
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('A1103', '2');"; //13
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('All04', '2');"; //14
        db.execSQL(sql);
        sql = "INSERT INTO Room (\"name\", \"building_id\") VALUES ('A1105', '2');"; //15
        db.execSQL(sql);

        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('1', '1', '1', 'S17', '12:45', '14:15', 'TH', '1');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('1', '1', '1', 'S18', '14:30', '16:00', 'TH', '2');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('1', '1', '1', 'S19', '12:45', '14:15', 'MW', '3');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('2', '2', '1', 'S17', '14:30', '16:00', 'MW', '11');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('2', '2', '1', 'S18', '09:15', '10:45', 'MW', '12');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('2', '2', '1', 'S19', '12:45', '14:15', 'MW', '6');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('3', '2', '1', 'S11A', '09:15', '10:45', 'MW', '5');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('3', '2', '1', 'S17A', '11:00', '12:30', 'MW', '5');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('3', '2', '1', 'S18A', '12:45', '14:15', 'TH', '6');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('4', '3', '1', 'S17', '12:45', '14:15', 'TH', '7');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('4', '3', '1', 'S18', '11:00', '12:30', 'MW', '8');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('4', '3', '1', 'S19', '11:00', '12:30', 'TH', '8');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('5', '3', '1', 'S17', '12:45', '14:15', 'TH', '7');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('5', '3', '1', 'S18', '12:45', '14:15', 'MW', '9');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('5', '3', '1', 'S19', '11:00', '12:30', 'TH', '8');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('5', '3', '1', 'S21', '14:30', '16:00', 'MW', '4');";
        db.execSQL(sql);
        sql = "INSERT INTO CourseOffering (\"course_id\", \"faculty_id\", \"term_id\", \"section\", \"time_start\", \"time_end\", \"days\", \"room_id\") VALUES ('5', '3', '1', 'S21', '16:15', '17:45', 'MW', '5');";
        db.execSQL(sql);

        sql = "INSERT INTO Rotation (\"id\") VALUES ('A');";
        db.execSQL(sql);
        sql = "INSERT INTO Rotation (\"id\") VALUES ('B');";
        db.execSQL(sql);
        sql = "INSERT INTO Rotation (\"id\") VALUES ('C');";
        db.execSQL(sql);
        sql = "INSERT INTO Rotation (\"id\") VALUES ('D');";
        db.execSQL(sql);
        sql = "INSERT INTO Rotation (\"id\") VALUES ('E');";
        db.execSQL(sql);

        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '1');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '2');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '3');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '4');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '5');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '11');";
        db.execSQL(sql);
        sql = "INSERT INTO RotationRoom (\"rotation_id\", \"room_id\") VALUES ('A', '12');";
        db.execSQL(sql);

        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('AB', 'Absent', 'The faculty is not in class after the first third of the official class time.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('ED', 'Early Dismissal', 'The faculty dismissed the class earlier than the official class time.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('LA', 'Late', 'The faculty is not in class after 5 minutes from the start of the official class time.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('PR', 'Present', 'The faculty is present at the start of the official class time.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('SB', 'Substitute', 'The faculty inside the classroom is not hte official faculty member assigned to the class.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('SW', 'Seatwork', 'The faculty is not in class, but the students are performing class work.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('US', 'Unscheduled Class', 'A room is occupied by a faculty and their students without prior notice.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('VR', 'Vacant Room', 'Both the faculty and the students are not in class.');";
        db.execSQL(sql);
        sql = "INSERT INTO attendancestatus (\"code\", \"name\", \"description\") VALUES ('CE', 'Checker Error', 'Exceeded class time.');";
        db.execSQL(sql);

        ContentValues cv = new ContentValues();

        cv = new ContentValues();
        cv.put(Faculty.COL_FNAME, "Remedios");
        cv.put(Faculty.COL_MNAME, "de Dios");
        cv.put(Faculty.COL_LNAME, "Bulos");
        cv.put(Faculty.COL_COLLEGE, "College of Computer Studies");
        cv.put(Faculty.COL_EMAIL, "remediosdedios@yahoo.com");
        cv.put(Faculty.COL_MOBNUM, "09175148169");
        cv.put(Faculty.COL_PIC, drawableToByteArray(ContextCompat.getDrawable(context, R.drawable.bulos)));
        cv.put(Faculty.COL_DEPT, "ST Department");
        db.insert(Faculty.TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(Faculty.COL_FNAME, "Florante");
        cv.put(Faculty.COL_MNAME, "R.");
        cv.put(Faculty.COL_LNAME, "Salvador");
        cv.put(Faculty.COL_COLLEGE, "College of Computer Studies");
        cv.put(Faculty.COL_EMAIL, "florante.salvador@dlsu.edu.ph");
        cv.put(Faculty.COL_MOBNUM, "09175148169");
        cv.put(Faculty.COL_PIC, drawableToByteArray(ContextCompat.getDrawable(context, R.drawable.salvador)));
        cv.put(Faculty.COL_DEPT, "ST Department");
        db.insert(Faculty.TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(Faculty.COL_FNAME, "Stanley");
        cv.put(Faculty.COL_MNAME, "Y.");
        cv.put(Faculty.COL_LNAME, "Tan");
        cv.put(Faculty.COL_COLLEGE, "College of Computer Studies");
        cv.put(Faculty.COL_EMAIL, "daniel.tan@dlsu.edu.ph");
        cv.put(Faculty.COL_MOBNUM, "09175148169");
        cv.put(Faculty.COL_PIC, drawableToByteArray(ContextCompat.getDrawable(context, R.drawable.tan)));
        cv.put(Faculty.COL_DEPT, "ST Department");
        db.insert(Faculty.TABLE_NAME, null, cv);

        String query;

        query = "INSERT INTO Attendance " +
                "(courseoffering_id) " +
                "SELECT co.id " +
                "from CourseOffering co inner join Room r on co.room_id = r.id " +
                "inner join RotationRoom rr on r.id = rr.room_id;";

        db.execSQL(query);

        sql = "INSERT INTO Substitute (\"faculty_id\") VALUES ('2');";
        db.execSQL(sql);

        sql = "INSERT INTO MakeupClass (\"new_start_time\", \"new_end_time\", \"date\", \"new_rm_id\", \"reason\", \"attendance_id\", \"sub_id\") VALUES ('12:45', '14:45', '2016-11-23', '1', 'AC', '3', '2');";
        db.execSQL(sql);
    }

    private byte[] drawableToByteArray(Drawable dr) {
        Drawable d = dr; // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public CheckerAccount checkIfUserExists(String username) {
        CheckerAccount u = new CheckerAccount();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(CheckerAccount.TABLE_NAME, null, " " + CheckerAccount.COL_UN + "=? ", new String[]{username}, null, null, null);

        if (c.moveToFirst()) {
            u.setCheckerid(c.getInt(c.getColumnIndex(CheckerAccount.COL_ID)));
            u.setFname(c.getString(c.getColumnIndex(CheckerAccount.COL_FNAME)));
            u.setMname(c.getString(c.getColumnIndex(CheckerAccount.COL_MNAME)));
            u.setLname(c.getString(c.getColumnIndex(CheckerAccount.COL_LNAME)));
            u.setUn(c.getString(c.getColumnIndex(CheckerAccount.COL_UN)));
            u.setEmail(c.getString(c.getColumnIndex(CheckerAccount.COL_EMAIL)));
            u.setPw(c.getString(c.getColumnIndex(CheckerAccount.COL_PW)));
            u.setRid(c.getString(c.getColumnIndex(CheckerAccount.COL_RID)));

        } else {
            u = null;
        }

        c.close();
        db.close();

        return u;
    }
}

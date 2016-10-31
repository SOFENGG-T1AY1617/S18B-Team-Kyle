package com.example.avggo.attendancechecker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.avggo.attendancechecker.R;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    TextView facultyName, facultyCollege, courseCode, courseName, classTime, roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item);

        facultyName = (TextView) findViewById(R.id.facultyName);
        facultyCollege = (TextView) findViewById(R.id.facultyCollege);
        courseCode = (TextView) findViewById(R.id.courseCode);
        courseName = (TextView) findViewById(R.id.courseName);
        classTime = (TextView) findViewById(R.id.classTime);
        roomName = (TextView) findViewById(R.id.roomName);

        String sfacultyName = getIntent().getStringExtra("FNAME");
        String sfacultyCollege = getIntent().getStringExtra("COLLEGE");
        String scourseCode = getIntent().getStringExtra("COURSE_C");
        String scourseName = getIntent().getStringExtra("COURSE_N");
        String sclassTime = getIntent().getStringExtra("TIME");
        String sroomName = getIntent().getStringExtra("ROOM_N");

        facultyName.setText(sfacultyName);
        facultyCollege.setText(sfacultyCollege);
        courseCode.setText(scourseCode);
        courseName.setText(scourseName);
        classTime.setText(sclassTime);
        roomName.setText(sroomName);
    }
}

package com.example.avggo.attendancechecker.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.avggo.attendancechecker.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    CircleImageView facultyImage;
    TextView facultyName, facultyCollege, courseCode, courseName, classTime, roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item);

        facultyImage = (CircleImageView) findViewById(R.id.facultyImage);
        facultyName = (TextView) findViewById(R.id.facultyName);
        facultyCollege = (TextView) findViewById(R.id.facultyCollege);
        courseCode = (TextView) findViewById(R.id.courseCode);
        courseName = (TextView) findViewById(R.id.courseName);
        classTime = (TextView) findViewById(R.id.classTime);
        roomName = (TextView) findViewById(R.id.roomName);

        byte[] sFacultyImage = getIntent().getByteArrayExtra("PIC");
        String sFacultyName = getIntent().getStringExtra("FNAME");
        String sFacultyCollege = getIntent().getStringExtra("COLLEGE");
        String sCourseCode = getIntent().getStringExtra("COURSE_C");
        String sCourseName = getIntent().getStringExtra("COURSE_N");
        String sClassTime = getIntent().getStringExtra("TIME");
        String sRoomName = getIntent().getStringExtra("ROOM_N");

        facultyImage.setImageBitmap(BitmapFactory.decodeByteArray(sFacultyImage, 0, sFacultyImage.length));
        facultyName.setText(sFacultyName);
        facultyCollege.setText(sFacultyCollege);
        courseCode.setText(sCourseCode);
        courseName.setText(sCourseName);
        classTime.setText(sClassTime);
        roomName.setText(sRoomName);
    }
}

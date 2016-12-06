package com.example.avggo.attendancechecker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.model.UnscheduledClass;

public class ReportUCActivity extends AppCompatActivity {

    EditText roomName, facultyName, remarks;
    Button submit;
    DatabaseOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_uc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        toolbar.setTitle("Report Unscheduled Class");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        roomName = (EditText) findViewById(R.id.roomNameReport);
        facultyName = (EditText) findViewById(R.id.facultyNameReport);
        remarks = (EditText) findViewById(R.id.remarkReport);
        submit = (Button) findViewById(R.id.submitUC);

        db = new DatabaseOpenHelper(getBaseContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnscheduledClass uc = new UnscheduledClass();

                if(roomName.getText().toString().length() != 0 && facultyName.getText().toString().length() != 0 && remarks.getText().toString() .length() != 0 && db.roomFound(roomName.getText().toString()) && db.facultyFound(facultyName.getText().toString())) {
                    uc.setRoomName(roomName.getText().toString());
                    uc.setFaculty(facultyName.getText().toString());
                    uc.setRemarks(remarks.getText().toString());
                    Log.i("YAAAAAAS", "YES");
                    db.addUnscheduledClass(uc);

                    roomName.setText("");
                    facultyName.setText("");
                    remarks.setText("");

                    Toast.makeText(getBaseContext(), "Unscheduled attendance added.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getBaseContext(), "Invalid input found.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

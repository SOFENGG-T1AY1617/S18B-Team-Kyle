package com.example.avggo.attendancechecker.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.avggo.attendancechecker.R;

/**
 * Created by John Paul San Pedro on 02/11/2016.
 */

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar)findViewById(R.id.helpToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Help");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        findViewById(R.id.layout_ab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Absent");
                aDBuilder.setMessage("The faculty is not in class after the first third of the official class time.")
                         .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_ed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Early Dismissal");
                aDBuilder.setMessage("The faculty dismissed the class earlier than the official class time.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_la).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Late");
                aDBuilder.setMessage("The faculty is not in class after 5 minutes from the start of the official class time.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_pr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Present");
                aDBuilder.setMessage("The faculty is present at the start of the official class time.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_sb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Substitute");
                aDBuilder.setMessage("The faculty inside the classroom is not hte official faculty member assigned to the class.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_sw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Seatwork");
                aDBuilder.setMessage("The faculty is not in class, but the students are performing class work.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Unscheduled Class");
                aDBuilder.setMessage("A room is occupied by a faculty and their students without prior notice.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });

        findViewById(R.id.layout_vr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDBuilder = new AlertDialog.Builder(HelpActivity.this, R.style.MyDialog);
                aDBuilder.setTitle("Vacant Room");
                aDBuilder.setMessage("Both the faculty and the students are not in class.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                aDBuilder.show();
            }
        });


    }
}

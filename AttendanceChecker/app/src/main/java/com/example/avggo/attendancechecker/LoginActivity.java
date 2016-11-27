package com.example.avggo.attendancechecker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avggo.attendancechecker.model.CheckerAccount;

public class LoginActivity extends AppCompatActivity {

    public static int CHECKER_ID;
    public static String CHECKER_NAME = "";

    DatabaseOpenHelper db;
    EditText usernameET, passwordET;
    Button login;
    CheckerAccount u;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseOpenHelper(getBaseContext());
        usernameET = (EditText) findViewById(R.id.username_et);
        passwordET = (EditText) findViewById(R.id.password_et);

        usernameET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimationUtils.createCircularReveal(usernameET,
                            (int) event.getX(),
                            (int) event.getY(),
                            0,
                            usernameET.getHeight() * 2).start();
                }
                return false;
            }
        });

        passwordET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ViewAnimationUtils.createCircularReveal(passwordET,
                            (int) event.getX(),
                            (int) event.getY(),
                            0,
                            passwordET.getHeight() * 2).start();
                }
                return false;
            }
        });

        login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        });
    }

    private void clearFields() {
        usernameET.setText("");
        passwordET.setText("");
    }

    class LoginTask extends AsyncTask<Object, Void, String> {

        LoginTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            username = usernameET.getText().toString();
            password = passwordET.getText().toString();
        }

        @Override
        protected String doInBackground(Object... params) {
            u = db.checkIfUserExists(username);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent homepage = new Intent();

            if (username.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill up all the fields.", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                if (u == null) {
                    Toast.makeText(getApplicationContext(), "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    String checkPassword = u.getPw();
                    if (password.equals(checkPassword)) {
                        CHECKER_ID = u.getCheckerid();
                        CHECKER_NAME = u.getFname() + " " + u.getLname();

                        homepage.putExtra("DISPLAY_NAME", u.getFname() + " " + u.getLname());
                        homepage.putExtra("EMAIL", u.getEmail());
                        homepage.putExtra("RID", u.getRid());
                        homepage.putExtra("PIC", u.getPic());

                        Toast.makeText(getApplicationContext(), "Welcome " + CHECKER_NAME + "!", Toast.LENGTH_LONG).show();
                        homepage.setClass(getBaseContext(), MainActivity.class);
                        startActivity(homepage);
                        clearFields();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect username or password.", Toast.LENGTH_LONG).show();
                        clearFields();
                    }
                }
            }

            super.onPostExecute(result);
        }
    }
}
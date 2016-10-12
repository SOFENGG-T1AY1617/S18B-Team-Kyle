package com.example.avggo.attendancechecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avggo.attendancechecker.model.CheckerAccount;
import com.example.avggo.attendancechecker.ui.ListActivity;

public class LoginActivity extends AppCompatActivity {

    public static int CHECKER_ID;
    public static String CHECKER_NAME = "";

    DatabaseOpenHelper db;
    EditText usernameET, passwordET;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseOpenHelper(getBaseContext());
        usernameET = (EditText)findViewById(R.id.username_et);
        passwordET = (EditText)findViewById(R.id.password_et);
        login = (Button)findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent homepage = new Intent();

                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                CheckerAccount u = db.checkIfUserExists(username);

                if(username.equals("") || password.equals("")) {
                    Toast.makeText(getBaseContext(), "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (u == null) {
                        Toast.makeText(v.getContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        String checkPassword = u.getPw();
                        if (password.equals(checkPassword)) {
                            CHECKER_ID = u.getCheckerid();
                            CHECKER_NAME = u.getFname() + " " + u.getLname();

                            Toast.makeText(v.getContext(), "Welcome " + CHECKER_NAME, Toast.LENGTH_LONG).show();
                            homepage.setClass(getBaseContext(), ListActivity.class);
                            startActivity(homepage);
                        }
                        else
                            Toast.makeText(v.getContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}

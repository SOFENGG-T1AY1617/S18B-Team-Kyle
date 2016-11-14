package com.example.avggo.attendancechecker.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.model.Attendance;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.avggo.attendancechecker.R.id.radioButton;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    CircleImageView facultyImage;
    TextView facultyName, facultyCourse, courseCode, roomName;
    Attendance item;

    Button submitButton;


    RadioGroup rg1, rg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_v2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        facultyImage = (CircleImageView) findViewById(R.id.facultyImage);
        facultyName = (TextView) findViewById(R.id.facultyName);
        facultyCourse = (TextView) findViewById(R.id.facultyCourse);
        roomName = (TextView) findViewById(R.id.facultyRoom);
        submitButton = (Button) findViewById(R.id.submitButton);


        item = (Attendance) getIntent().getSerializableExtra("ATTENDANCE_ITEM");

        facultyImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));//sFacultyImage, 0, sFacultyImage.length));
        facultyName.setText(item.getFname());
        facultyCourse.setText(item.getCoursecode());
        roomName.setText(item.getRoom());

        rg1 = (RadioGroup) findViewById(R.id.codeRadioGroup1);
        rg2 = (RadioGroup) findViewById(R.id.codeRadioGroup2);

        rg1.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        rg2.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);

        setSelectedCode(item.getCode());

        setListeners();
    }

    private void setSelectedCode(String code){
        if(code != null){
            //loop through buttons
            int count = rg1.getChildCount();
            for (int i=0;i<count;i++) {
                RadioButton btn = (RadioButton) rg1.getChildAt(i);
                //Log.i("tagg", (String)btn.getText() + "----------" + i);
                if(btn.getText().equals(code)) {
                    btn.setChecked(true);
                    break;
                }
            }

            count = rg2.getChildCount();
            for (int i=0;i<count;i++) {
                RadioButton btn = (RadioButton) rg2.getChildAt(i);

                if(btn.getText().equals(code)) {
                    btn.setChecked(true);
                    break;
                }
            }
        }
    }

    private void setListeners(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = getSelectedItem();
                if (code != null){
                    item.setCode(code);

                    Intent intent = new Intent();
                    intent.putExtra("CODED_ATTENDANCE_ITEM", item);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private String getSelectedItem(){

        int chkId1 = rg1.getCheckedRadioButtonId();
        int chkId2 = rg2.getCheckedRadioButtonId();
        int realCheck = chkId1 == -1 ? chkId2 : chkId1;

        RadioButton radioButton = (RadioButton) findViewById(realCheck);

       // Toast.makeText(DetailActivity.this,
        //        radioButton.getText(), Toast.LENGTH_SHORT).show();

        return radioButton != null ? (String) radioButton.getText() : null;
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                rg2.clearCheck(); // clear the second RadioGroup!
                rg2.setOnCheckedChangeListener(listener2); //reset the listener
               // Log.e("XXX2", "do the work");
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                //Log.e("XXX2", "do the work");
            }
        }
    };
}

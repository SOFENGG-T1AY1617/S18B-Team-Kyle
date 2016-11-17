package com.example.avggo.attendancechecker.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avggo.attendancechecker.DatabaseOpenHelper;
import com.example.avggo.attendancechecker.MainActivity;
import com.example.avggo.attendancechecker.R;
import com.example.avggo.attendancechecker.model.Attendance;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.avggo.attendancechecker.R.id.middleLayout;
import static com.example.avggo.attendancechecker.R.id.radioButton;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    CircleImageView facultyImage;
    TextView facultyName, facultyCourse, courseCode, roomName, classTime;
    Attendance item;

    Button submitButton, ab, ed, la, pr, sb, sw, us, vr;
    String currentCode;
    EditText remark;

    RadioGroup rg1, rg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_v3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        toolbar.setTitle("Class Details");
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
        classTime = (TextView) findViewById(R.id.classTime);
        submitButton = (Button) findViewById(R.id.submitButton);
        remark = (EditText) findViewById(R.id.remarkField);


        item = (Attendance) getIntent().getSerializableExtra("ATTENDANCE_ITEM");

        facultyImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getPic(), 0, item.getPic().length));//sFacultyImage, 0, sFacultyImage.length));
        facultyName.setText(item.getFname());
        facultyCourse.setText(item.getCoursecode());
        roomName.setText(item.getRoom());
        classTime.setText(item.getStartTime() + " - " + item.getEndTime());

        /*rg1 = (RadioGroup) findViewById(R.id.codeRadioGroup1);
        rg2 = (RadioGroup) findViewById(R.id.codeRadioGroup2);

        rg1.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        rg2.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);

        setSelectedCode(item.getCode());

        if(MainActivity.submitted){
            LinearLayout l = (LinearLayout) findViewById(R.id.middleLayout);
            l.setVisibility(View.GONE);
        }*/

        ab = (Button) findViewById(R.id.abBtn);
        ed = (Button) findViewById(R.id.edBtn);
        la = (Button) findViewById(R.id.laBtn);
        pr = (Button) findViewById(R.id.prBtn);
        sb = (Button) findViewById(R.id.sbBtn);
        sw = (Button) findViewById(R.id.swBtn);
        us = (Button) findViewById(R.id.usBtn);
        vr = (Button) findViewById(R.id.vrBtn);

        if(MainActivity.submitted){
            disableButtons();
            remark.setEnabled(false);
            submitButton.setVisibility(View.GONE);
            //submitButton.setVisibility(View.GONE);
        }
        setListeners();
        setSelectedCode(item.getCode());
    }

    public void disableButtons(){
        ab.setEnabled(false);
        ed.setEnabled(false);
        la.setEnabled(false);
        pr.setEnabled(false);
        pr.setEnabled(false);
        sb.setEnabled(false);
        sw.setEnabled(false);
        us.setEnabled(false);
        vr.setEnabled(false);
    }

    public void setListeners(){
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = ab.getText().toString();
                ab.setTextColor(Color.WHITE);
                ab.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = ed.getText().toString();
                ed.setTextColor(Color.WHITE);
                ed.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = la.getText().toString();
                la.setTextColor(Color.WHITE);
                la.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = pr.getText().toString();
                pr.setTextColor(Color.WHITE);
                pr.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = sb.getText().toString();
                sb.setTextColor(Color.WHITE);
                sb.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = sw.getText().toString();
                sw.setTextColor(Color.WHITE);
                sw.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = us.getText().toString();
                us.setTextColor(Color.WHITE);
                us.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                vr.setTextColor(Color.BLACK);
                vr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCode = vr.getText().toString();
                vr.setTextColor(Color.WHITE);
                vr.getBackground().setColorFilter(Color.rgb(10, 153, 61), PorterDuff.Mode.MULTIPLY);
                ed.setTextColor(Color.BLACK);
                ed.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                la.setTextColor(Color.BLACK);
                la.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                pr.setTextColor(Color.BLACK);
                pr.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sb.setTextColor(Color.BLACK);
                sb.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                sw.setTextColor(Color.BLACK);
                sw.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                us.setTextColor(Color.BLACK);
                us.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                ab.setTextColor(Color.BLACK);
                ab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCode != null){
                    item.setCode(currentCode);
                    item.setRemarks(remark.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("CODED_ATTENDANCE_ITEM", item);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void setSelectedCode(String code){
        remark.setText(item.getRemarks());
        if(code != null){
            if(code.equals("Absent")){
                ab.performClick();
            }
            else if(code.equals("Early Dismissal")){
                ed.performClick();
            }
            else if(code.equals("Late")){
                la.performClick();
            }
            else if(code.equals("Present")){
                pr.performClick();
            }
            else if(code.equals("Seatwork")){
                sw.performClick();
            }
            else if(code.equals("Substitute")){
                sb.performClick();
            }
            else if(code.equals("Unscheduled")){
                us.performClick();
            }
            else if(code.equals("Vacant Room")){
                vr.performClick();
            }
        }
    }

    /*private void setSelectedCode(String code){
        if(code != null){
            //loop through buttons
            int count = rg1.getChildCount();
            for (int i=0;i<count;i++) {
                RadioButton btn = (RadioButton) rg1.getChildAt(i);
                Log.i("tagg", (String)btn.getText() + "----------" + i);
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
    }*/
}

package com.example.avggo.attendancechecker.meneger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.avggo.attendancechecker.model.AttendanceDateModel;
import com.google.gson.Gson;

/**
 * Created by Bryan on 12/6/2016.
 */

public class AttendanceDateManager {

    public static final String ATTENDANCE_DATE_TAG = "heheheheheh";

    private  static AttendanceDateModel dateMedel;
    private static boolean initialized = false;


    public static void saveState(Context context){
        SharedPreferences mPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dateMedel);
        prefsEditor.putString(ATTENDANCE_DATE_TAG, json);
        prefsEditor.commit();
        Log.i("tagg", "SubmitManager.saveState() size of dateModel is " + dateMedel.getSubmittedDates().size());
    }

    public static void resumeState(Context context){
        initialized = true;

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = app_preferences.getString(ATTENDANCE_DATE_TAG, null);
        dateMedel = gson.fromJson(json, AttendanceDateModel.class);
        Log.i("tagg", "SubmitManager.resumeState() dateModel is null? " + (dateMedel == null));
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param date
     */
    public static void addAttendanceDate(String date){
        checkForErrors();
        dateMedel.addAttendanceDate(date);
        Log.i("tagg", "SubmitState.submitToDate()");
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param date
     * @return
     */
    public static boolean isAttendanceDate(String date){
        checkForErrors();
        Log.i("tagg", "SubmitManager.isSubmittedDate() returns " + dateMedel.hasDate(date));
        return dateMedel.hasDate(date);
    }

    private static void checkForErrors(){
        if(!initialized){
            throw new ExceptionInInitializerError();
        }
        if(dateMedel == null){
            dateMedel = new AttendanceDateModel();
        }
    }

}

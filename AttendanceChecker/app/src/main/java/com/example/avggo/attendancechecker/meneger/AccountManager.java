/*package com.example.avggo.attendancechecker.meneger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.avggo.attendancechecker.model.SebmetMeneger;
import com.google.gson.Gson;

/**
 * Created by avggo on 12/14/2016.


public class AccountManager {

    public static final String SEBMET_MEDEL_TAG = "heheheheheh";

    private  static SebmetMeneger submitManager;
    private static AttendanceDateManager dateManager;


    public static void saveState(Context context){
        SharedPreferences mPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(submitManager);
        prefsEditor.putString(SEBMET_MEDEL_TAG, json);
        prefsEditor.commit();
        Log.i("tagg", "SubmitManager.saveState() size of dateModel is " + submitManager.getSubmittedDates().size());
    }

    public static void resumeState(Context context){
        //initialized = true;

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = app_preferences.getString(SEBMET_MEDEL_TAG, null);
        submitManager = gson.fromJson(json, SebmetMeneger.class);
        Log.i("tagg", "SubmitManager.resumeState() dateModel is null? " + (submitManager == null));
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param date
     
    public static void submitToDate(String date){
        checkForErrors();
        submitManager.addSubmittedDate(date);
        Log.i("tagg", "SubmitState.submitToDate()");
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param date
     * @return

    public static boolean isSubmittedDate(String date){
        checkForErrors();
        Log.i("tagg", "SubmitManager.isSubmittedDate() returns " + submitManager.hasDate(date));
        return submitManager.hasDate(date);
    }

    private static void checkForErrors(){
        if(!initialized){
            throw new ExceptionInInitializerError();
        }
        if(submitManager == null){
            submitManager = new SebmetMeneger();
        }
    }
}
*/
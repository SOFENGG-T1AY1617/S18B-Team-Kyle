package com.example.avggo.attendancechecker.meneger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.avggo.attendancechecker.model.SebmetMedel;
import com.google.gson.Gson;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by avggo on 11/24/2016.
 */

public class SebmetMeneger {

    public static final String SEBMET_MEDEL_TAG = "heheheheheh";

    private  static SebmetMedel dateMedel;
    private static boolean initialized = false;


    public static void saveState(Context context){
        SharedPreferences mPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dateMedel);
        prefsEditor.putString(SEBMET_MEDEL_TAG, json);
        prefsEditor.commit();
    }

    public static void resumeState(Context context){
        initialized = true;

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = app_preferences.getString(SEBMET_MEDEL_TAG, null);
        dateMedel = gson.fromJson(json, SebmetMedel.class);
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param date
     */
    public static void submitToDate(String date){
        if(!initialized){
            throw new ExceptionInInitializerError();
        }
        if(dateMedel == null){
            dateMedel = new SebmetMedel();
        }

        dateMedel.addSubmittedDate(date);
    }

    public static boolean isSubmiitedDate(String date){
        return dateMedel.hasDate(date);
    }


}

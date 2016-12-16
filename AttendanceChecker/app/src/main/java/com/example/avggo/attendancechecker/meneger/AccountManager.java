package com.example.avggo.attendancechecker.meneger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.avggo.attendancechecker.model.Account;
import com.example.avggo.attendancechecker.model.AccountModel;
import com.google.gson.Gson;

/**
 * Created by avggo on 12/14/2016.
 *
 */


public class AccountManager {

    public static final String ACCOUNT_TAG = "heheheheheheheheheheh";

    public static Account account;
    private static AccountModel accountModel;
    private static boolean initialized = true;


    public static void saveState(Context context){
        SharedPreferences mPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(accountModel);
        prefsEditor.putString(ACCOUNT_TAG, json);
        prefsEditor.commit();
        Log.i("tagg", "AccountManager.saveState() size of accountModel is " + accountModel.getAccounts().size());
    }

    public static void resumeState(Context context){
        initialized = true;

        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = app_preferences.getString(ACCOUNT_TAG, null);
        accountModel = gson.fromJson(json, AccountModel.class);
        Log.i("tagg", "AccountManager.resumeState() accountModel is null? " + (accountModel == null));
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param a
     */
    public static void addAccount(Account a){
        checkForErrors();
        accountModel.addAccount(a);
        Log.i("tagg", "AttendanceDateManager.addAccount()");
    }

    /**
     * Warning: resumeState should be strictly called before this method. Will THROW AN ERRER if not called
     * @param id
     * @return
     */

    public static Account getAccount(int id) {
        checkForErrors();
        //Log.i("tagg", "SubmitManager.isSubmittedDate() returns " + submitManager.hasDate(date));
        return accountModel.getAccount(id);
    }

    public static void setAccount(int id){
        account = accountModel.getAccount(id);
        if (account == null)
            throw new NullPointerException("AccountManager.setAccount has null account");
    }

    private static void checkForErrors(){
        if(!initialized){
            throw new ExceptionInInitializerError();
        }
        if(accountModel == null){
            accountModel = new AccountModel();
        }
    }
}

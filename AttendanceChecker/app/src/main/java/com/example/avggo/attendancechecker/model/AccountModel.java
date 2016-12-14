package com.example.avggo.attendancechecker.model;

import java.util.ArrayList;

/**
 * Created by avggo on 12/14/2016.
 */

public class AccountModel {

    private ArrayList<Account> arreyLest = new ArrayList<Account>();


    public ArrayList getAccounts() {
        return arreyLest;
    }

    public void addAccount(Account a) {
        if (!arreyLest.contains(a)) {
            arreyLest.add(a);
        }
    }

    public boolean hasAccountID(int id){

        for(Account a : arreyLest){
            if(a.getId() == id)
                return true;
        }

        return false;
    }

    public Account getAccount(int id){
        for(Account a : arreyLest){
            if(a.getId() == id)
                return a;
        }

        return null;
    }
}

package com.deshario.mbhealthrecord;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deshario on 12/23/2016.
 */

public class FirstTimeData {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _mycontext;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String CONFIG_NAME = "userfirsttime";

    private static final String IS_FIRST_DATA = "IsFirstData";

    public FirstTimeData(Context context) {
        this._mycontext = context;
        pref = _mycontext.getSharedPreferences(CONFIG_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimedata(String u_nickname,String u_weight, String u_height, String u_week) {
        editor.putString("Nickname",u_nickname);
        editor.putString("Weight",u_weight);
        editor.putString("Height",u_height);
        editor.putString("Week",u_week);
        editor.commit();
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_DATA, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_DATA, true);
    }


}

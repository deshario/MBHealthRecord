package com.deshario.mbhealthrecord;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deshario on 12/23/2016.
 */
   public class LoggedIN {
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        Context _context;

        // shared pref mode
        int PRIVATE_MODE = 0;

        // Shared preferences file name
        private static final String PREF_NAME = "logged_in_or_not";

        private static final String IS_LOGGEDIN = "IsLoggedin";

        public LoggedIN(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void setLoginData(String user, String email, String pass) {
            editor.putString("login_user",user);
            editor.putString("login_email",email);
            editor.putString("login_pass",pass);
            editor.commit();
        }

        public void setLogin(boolean isFirstTime) {
            editor.putBoolean(IS_LOGGEDIN, isFirstTime);
            editor.commit();
        }

        public boolean isloggedin() {
            return pref.getBoolean(IS_LOGGEDIN, true);
        }

    }

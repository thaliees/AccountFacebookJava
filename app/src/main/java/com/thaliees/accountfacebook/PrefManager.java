package com.thaliees.accountfacebook;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "AccountFacebook";
    // Session preference
    private static final String IS_LOGIN = "IsLoginIn";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public boolean isLogin(){ return pref.getBoolean(IS_LOGIN, false); }

    public void setIsLogin(boolean isLogin){
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }
}

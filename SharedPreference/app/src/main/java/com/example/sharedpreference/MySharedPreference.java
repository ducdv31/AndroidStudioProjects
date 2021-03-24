package com.example.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

class MySharedPreference {
    private static final String MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE";
    private Context context;

    public MySharedPreference(Context context) {
        this.context = context;
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}

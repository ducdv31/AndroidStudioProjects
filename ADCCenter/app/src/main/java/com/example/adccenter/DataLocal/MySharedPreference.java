package com.example.adccenter.DataLocal;

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

    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void putIntValue(String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public void putFloatValue(String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloatValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

}

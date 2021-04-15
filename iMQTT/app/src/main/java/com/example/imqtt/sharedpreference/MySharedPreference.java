package com.example.imqtt.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private static final String MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE";
    private final Context context;

    public MySharedPreference(Context context) {
        this.context = context;
    }

    /* Boolean */

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

    /* Int */
    public void putIntValue(String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /* Float */
    public void putFloatValue(String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloatValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

    /* Long */
    public void putDoubleValue(String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getDoubleValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    /* String */
    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}

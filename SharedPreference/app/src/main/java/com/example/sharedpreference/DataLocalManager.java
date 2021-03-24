package com.example.sharedpreference;

import android.content.Context;

public class DataLocalManager {
    private static final String PRE_FIRST = "PRE_FIRST";
    private static DataLocalManager instance;
    private MySharedPreference mySharedPreference;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreference = new MySharedPreference(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setFirstInstall(boolean isfirst) {
        DataLocalManager.getInstance().mySharedPreference.putBooleanValue(PRE_FIRST, isfirst);
    }

    public static boolean getFirstInstall() {
        return DataLocalManager.getInstance().mySharedPreference.getBooleanValue(PRE_FIRST);
    }
}

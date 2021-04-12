package com.example.mymqtt.sharedpreference;

import android.content.Context;

public class DataLocalManager {
    private static final String PRE_FIRST = "PRE_FIRST";
    private static final String NUM_LED = "NUM_LED";
    private static final String TOPIC_LED = "TOPIC_LED";
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

    public static void setNumLed(int num) {
        DataLocalManager.getInstance().mySharedPreference.putIntValue(NUM_LED, num);
    }

    public static int getNumLed() {
        return DataLocalManager.getInstance().mySharedPreference.getIntValue(NUM_LED);
    }

    public static void setTopicLed(String topic) {
        DataLocalManager.getInstance().mySharedPreference.putStringValue(TOPIC_LED, topic);
    }

    public static String getTopicLed() {
        return DataLocalManager.getInstance().mySharedPreference.getStringValue(TOPIC_LED);
    }
    
}

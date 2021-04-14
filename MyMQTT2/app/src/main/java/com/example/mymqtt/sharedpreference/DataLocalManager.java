package com.example.mymqtt.sharedpreference;

import android.content.Context;

public class DataLocalManager {
    private static final String PRE_FIRST = "PRE_FIRST";
    private static final String NUM_LED = "NUM_LED";
    private static final String TOPIC_LED = "TOPIC_LED";
    private static final String HOST_SAVE = "Host share preference";
    private static final String PORT_SAVE = "Port share preference";
    private static final String USERNAME_SAVE = "User name share preference";
    private static final String PASSWORD_SAVE = "Password share preference";
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

    public static void setHostMQTT(String hostMQTT) {
        DataLocalManager.getInstance().mySharedPreference.putStringValue(HOST_SAVE, hostMQTT);
    }

    public static String getHostMQTT() {
        return DataLocalManager.getInstance().mySharedPreference.getStringValue(HOST_SAVE);
    }

    public static void setPortMQTT(String portMQTT) {
        DataLocalManager.getInstance().mySharedPreference.putStringValue(PORT_SAVE, portMQTT);
    }

    public static String getPortMQTT() {
        return DataLocalManager.getInstance().mySharedPreference.getStringValue(PORT_SAVE);
    }

    public static void setUsernameMQTT(String usernameMQTT) {
        DataLocalManager.getInstance().mySharedPreference.putStringValue(USERNAME_SAVE, usernameMQTT);
    }

    public static String getUsernameMQTT() {
        return DataLocalManager.getInstance().mySharedPreference.getStringValue(USERNAME_SAVE);
    }

    public static void setPasswordMQTT(String passwordMQTT) {
        DataLocalManager.getInstance().mySharedPreference.putStringValue(PASSWORD_SAVE, passwordMQTT);
    }

    public static String getPasswordMQTT() {
        return DataLocalManager.getInstance().mySharedPreference.getStringValue(PASSWORD_SAVE);
    }

}

package com.example.myhouse.timeconverter;

public class TimeConverter2 {
    private static int Hours;
    private static int Minutes;

    public static String convertFromMinutes(int AllMinutes) {
        Hours = AllMinutes / 60;
        Minutes = AllMinutes % 60;
        if (Hours < 10 && Minutes < 10) {
            return "0" + Hours + " : 0" + Minutes;
        } else if (Hours < 10 && Minutes >= 10) {
            return "0" + Hours + " : " + Minutes;
        } else if (Hours >= 10 && Minutes < 10) {
            return Hours + " : 0" + Minutes;
        } else
            return Hours + " : " + Minutes;
    }

    public static int getHours(int AllMinutes) {
        Hours = AllMinutes / 60;
        return Hours;
    }

    public static int getMinutes(int AllMinutes) {
        Minutes = AllMinutes % 60;
        return Minutes;
    }
}

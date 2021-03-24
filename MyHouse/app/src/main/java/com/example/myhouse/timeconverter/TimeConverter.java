package com.example.myhouse.timeconverter;

public class TimeConverter {
    private int Hours;
    private int Minutes;

    public TimeConverter() {
    }

    public String ConvertFromMinutes(int AllMinutes) {
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

    public int getHours(int AllMinutes) {
        Hours = AllMinutes / 60;
        return Hours;
    }

    public int getMinutes(int AllMinutes) {
        Minutes = AllMinutes % 60;
        return Minutes;
    }
}

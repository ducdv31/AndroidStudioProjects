package com.example.myroom.timeconverter

class TimeConverter {
    private var Hours: Int? = null
    private var Minutes: Int? = null

    fun ConvertFromMinutes(AllMinutes: Int): String? {
        Hours = AllMinutes / 60
        Minutes = AllMinutes % 60
        return if (Hours!! < 10 && Minutes!! < 10) {
            "0$Hours : 0$Minutes"
        } else if (Hours!! < 10 && Minutes!! >= 10) {
            "0$Hours : $Minutes"
        } else if (Hours!! >= 10 && Minutes!! < 10) {
            Hours.toString() + " : 0" + Minutes
        } else Hours.toString() + " : " + Minutes
    }

    fun getHours(AllMinutes: Int): Int {
        Hours = AllMinutes / 60
        return Hours as Int
    }

    fun getMinutes(AllMinutes: Int): Int {
        Minutes = AllMinutes % 60
        return Minutes as Int
    }
}
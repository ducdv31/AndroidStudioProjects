package com.example.myhome.utils

object TimeConverter {
    private var Hours = 0
    private var Minutes = 0

    fun convertFromMinutes(AllMinutes: Int): String {
        Hours = AllMinutes / 60
        Minutes = AllMinutes % 60
        return if (Hours < 10 && Minutes < 10) {
            "0$Hours : 0$Minutes"
        } else if (Hours < 10 && Minutes >= 10) {
            "0$Hours : $Minutes"
        } else if (Hours >= 10 && Minutes < 10) {
            "$Hours : 0$Minutes"
        } else "$Hours : $Minutes"
    }

    fun getHours(AllMinutes: Int): Int {
        Hours = AllMinutes / 60
        return Hours
    }

    fun getMinutes(AllMinutes: Int): Int {
        Minutes = AllMinutes % 60
        return Minutes
    }
}
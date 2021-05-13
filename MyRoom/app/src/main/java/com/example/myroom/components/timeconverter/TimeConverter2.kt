package com.example.myroom.components.timeconverter

class TimeConverter2 {
    companion object {
        private var Hours: Int? = null
        private var Minutes: Int? = null
        private var Day: Int? = null

        fun convertFromMinutes(AllMinutes: Int): String {
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

        fun convertFromMinutesWithDay(AllMinutes: Int): String {
            /* cal day */
            Day = AllMinutes / 1440
            val minutesHM: Int = AllMinutes - Day!! * 1440
            /* cal Hours and Minutes */
            Hours = minutesHM / 60
            Minutes = minutesHM % 60
            return if (Hours!! < 10 && Minutes!! < 10) {
                "$Day day : 0$Hours : 0$Minutes"
            } else if (Hours!! < 10 && Minutes!! >= 10) {
                "$Day day : 0$Hours : $Minutes"
            } else if (Hours!! >= 10 && Minutes!! < 10) {
                "$Day day : " + Hours.toString() + " : 0" + Minutes
            } else "$Day day : " + Hours.toString() + " : " + Minutes
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
}
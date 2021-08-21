package com.example.myhome.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormatSymbols
import java.time.LocalDate

class Utils {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String {
            val localDate: LocalDate = LocalDate.now()
            val month =
                localDate.month.toString().substring(0, 1).uppercase() + localDate.month.toString()
                    .substring(1).lowercase()
            val day = if (localDate.dayOfMonth < 10) {
                "0${localDate.dayOfMonth}"
            } else {
                localDate.dayOfMonth
            }
            return "$day $month ${localDate.year}"
        }

        fun getMonth(num: Int): String {
            var month = ""
            val dfs = DateFormatSymbols()
            val months: Array<String> = dfs.months
            if (num in 0..11) {
                month = months[num]
            }
            return month
        }
    }
}
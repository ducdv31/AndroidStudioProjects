package vn.dv.myhome.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    fun getCurrentTime(): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.TIME_HH_MM)
        return LocalTime.now().format(dateTimeFormatter)
    }
}
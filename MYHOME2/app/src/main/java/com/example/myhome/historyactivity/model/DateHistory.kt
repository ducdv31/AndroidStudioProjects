package com.example.myhome.historyactivity.model

import java.io.Serializable

data class DateHistory(val date: String? = null) : Serializable {
    fun getDateFormatted(): String? {
        return date?.trim()?.replace(" ", " / ")
    }
}
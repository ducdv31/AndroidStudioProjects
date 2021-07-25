package com.example.myhome.historyactivity.model

import com.example.myhome.activitymain.model.Dht11Value
import com.example.myhome.tool.TimeConverter

data class DhtTimeValueModel(
    val time: Int? = null,
    val dht11Value: Dht11Value? = null
) {
    fun getTimeFormatted(): String? {
        return time?.let { TimeConverter.convertFromMinutes(it) }
    }
}
package com.example.myhome.data.model.dht

import com.example.myhome.utils.TimeConverter

data class DhtTimeValueModel(
    val time: Int? = null,
    val dht11Value: ThValue? = null
) {
    fun getTimeFormatted(): String? {
        return time?.let { TimeConverter.convertFromMinutes(it) }
    }
}
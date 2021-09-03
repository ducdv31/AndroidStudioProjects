package com.example.myhome.data.model.dht

import com.example.myhome.utils.TimeConverter
import java.io.Serializable

data class DhtTimeValueModel(
    val time: Int? = null,
    val dht11Value: ThValue? = null
) : Serializable {
    fun getTimeFormatted(): String? {
        return time?.let { TimeConverter.convertFromMinutes(it) }
    }
}
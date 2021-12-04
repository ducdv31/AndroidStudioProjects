package vn.deviot.mymqtt.net.mqtt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageMqtt(
    val topic: String?,
    val message: String?
) : Parcelable
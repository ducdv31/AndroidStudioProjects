package vn.deviot.mymqtt.net.mqtt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageMqtt(
    val topic: String?,
    val payload: ByteArray?,
    val qos: Int?,
    val id: Int?,
    val isRetain: Boolean?,
    val isDuplicate: Boolean?
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageMqtt

        if (topic != other.topic) return false
        if (payload != null) {
            if (other.payload == null) return false
            if (!payload.contentEquals(other.payload)) return false
        } else if (other.payload != null) return false
        if (qos != other.qos) return false
        if (id != other.id) return false
        if (isRetain != other.isRetain) return false
        if (isDuplicate != other.isDuplicate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topic?.hashCode() ?: 0
        result = 31 * result + (payload?.contentHashCode() ?: 0)
        result = 31 * result + (qos ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (isRetain?.hashCode() ?: 0)
        result = 31 * result + (isDuplicate?.hashCode() ?: 0)
        return result
    }
}
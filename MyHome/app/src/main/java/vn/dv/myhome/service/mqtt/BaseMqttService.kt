package vn.dv.myhome.service.mqtt

import android.app.Service
import vn.dv.myhome.utils.Constants

abstract class BaseMqttService : Service() {
    abstract fun connectUriMqtt(
        url: String,
        port: Int,
        username: String? = null,
        password: String? = null
    )

    abstract fun publishMqtt(
        topic: String = Constants.EMPTY,
        content: String = Constants.EMPTY,
        remain: Boolean = false,
        qos: Int = 0
    )

    abstract fun subscribeMqtt(
        topic: String = Constants.EMPTY,
        qos: Int = 0
    )

    abstract fun unSubscribeMqtt(topic: String)

    abstract fun disconnectMqtt()
}
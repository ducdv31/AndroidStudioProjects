package vn.deviot.mymqtt.net.mqtt

import android.app.Service
import vn.deviot.mymqtt.constans.EMPTY

abstract class MqttBlueprint : Service() {

    abstract fun connectUri(
        url: String,
        port: Int,
        username: String? = null,
        password: String? = null
    )

    abstract fun publish(topic: String = EMPTY, content: String = EMPTY, remain: Boolean = false)

    abstract fun subscribe(topic: String = EMPTY, qos: Int = 0)

    abstract fun unSubscribe(topic: String)

    abstract fun disconnect()
}
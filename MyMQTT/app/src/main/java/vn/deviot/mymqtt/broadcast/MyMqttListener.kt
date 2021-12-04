package vn.deviot.mymqtt.broadcast

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken

interface MyMqttListener {

    fun onConnectionLost(t: Throwable?)

    fun onServerConnected(status: Boolean)

    fun notifyConnected(notify: Boolean) // Requires connect

    fun onSubscribe(subscribe: Boolean)

    fun onMessageArrived(topic: String?, message: String?)

    fun onDeliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken?)

    fun onUnsubscribe(unsubscribe: Boolean)

    fun onDisconnect()
}
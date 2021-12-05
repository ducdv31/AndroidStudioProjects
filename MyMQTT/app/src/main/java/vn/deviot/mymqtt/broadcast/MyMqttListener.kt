package vn.deviot.mymqtt.broadcast

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken

interface MyMqttListener {

    fun onConnectionLost(t: String?)

    fun onConnected()

    fun onSubscribe()

    fun onMessageArrived(topic: String?, message: String?)

    fun onDeliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken?)

    fun onUnsubscribe()

    fun onDisconnect()
}
package vn.dv.myhome.service.mqtt

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage

interface IOnMqttSendAction {

    fun sendActionConnectSuccess(asyncActionToken: IMqttToken?)

    fun sendActionConnectLost(throwable: Throwable?)

    fun sendActionArrivedMessage(topic: String?, message: MqttMessage?)

    fun sendActionDeliveryCompleted(token: IMqttDeliveryToken?)

    fun sendActionSubscribeSuccess()

    fun sendActionUnSubscribeSuccess()

    fun sendActionError(throwable: Throwable?)

    fun sendActionNoConnected()

    fun sendActionDisconnected()
}
package vn.dv.myhome.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import vn.dv.myhome.service.mqtt.MqttActionKey
import vn.dv.myhome.utils.Constants

class MqttBroadcast : BroadcastReceiver() {

    private var iMqttBroadcastSendData: IMqttBroadcastSendData? = null

    fun addOnMqttListener(iMqttBroadcastSendData: IMqttBroadcastSendData) {
        this.iMqttBroadcastSendData = iMqttBroadcastSendData
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MqttActionKey.ConnectSuccess.name -> {
                val asyncActionToken: String? =
                    intent.getStringExtra(MqttActionKey.ConnectSuccess.name)
                iMqttBroadcastSendData?.onConnectSuccess(asyncActionToken)
            }
            MqttActionKey.ConnectLost.name -> {
                val message: String? =
                    intent.getStringExtra(MqttActionKey.ConnectLost.name)
                iMqttBroadcastSendData?.onConnectLost(message)
            }
            MqttActionKey.ArrivedMessage.name -> {
                val topic =
                    intent.getStringExtra(MqttActionKey.ArrivedMessage.name + Constants.TOPIC)
                val message =
                    intent.getStringExtra(MqttActionKey.ArrivedMessage.name + Constants.MESSAGE)
                iMqttBroadcastSendData?.onArrivedMessage(topic, message)
            }
            MqttActionKey.DeliveryCompleted.name -> {
                val token = intent.getStringExtra(MqttActionKey.DeliveryCompleted.name)
                iMqttBroadcastSendData?.onDeliveryCompleted(token)

            }
            MqttActionKey.SubscribeSuccess.name -> {
                iMqttBroadcastSendData?.onSubscribeSuccess()
            }
            MqttActionKey.UnSubscribeSuccess.name -> {
                iMqttBroadcastSendData?.onUnSubscribeSuccess()
            }
            MqttActionKey.Error.name -> {
                val message = intent.getStringExtra(MqttActionKey.Error.name)
                iMqttBroadcastSendData?.onError(message)
            }
            MqttActionKey.NoConnected.name -> {
                iMqttBroadcastSendData?.onNoConnected()
            }
            MqttActionKey.Disconnected.name -> {
                iMqttBroadcastSendData?.onDisconnected()
            }
        }
    }
}
package vn.deviot.mymqtt.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import vn.deviot.mymqtt.net.mqtt.action.MqttActionKey
import vn.deviot.mymqtt.net.mqtt.model.MessageMqtt

class MyBroadcast : BroadcastReceiver() {

    private val TAG = MyBroadcast::class.java.simpleName

    private var myMqttListener: MyMqttListener? = null

    fun addOnMqttListener(myMqttListener: MyMqttListener) {
        this.myMqttListener = myMqttListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MqttActionKey.ActionReceivedMessage.action -> {
                val messageMqtt: MessageMqtt? = intent.getParcelableExtra(MqttActionKey.ActionReceivedMessage.name)
                myMqttListener?.onMessageArrived(messageMqtt?.topic, messageMqtt?.payload.toString())
            }

            MqttActionKey.ActionDeliveryComplete.action -> {
                myMqttListener?.onDeliveryComplete(null)
            }

            MqttActionKey.ActionDisconnected.action -> {
                myMqttListener?.onDisconnect()
            }
            MqttActionKey.ActionConnectLost.name -> {
                myMqttListener?.onConnectionLost(null)
            }
        }
    }
}
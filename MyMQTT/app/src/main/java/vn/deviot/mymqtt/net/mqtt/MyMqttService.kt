package vn.deviot.mymqtt.net.mqtt

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import vn.deviot.mymqtt.net.mqtt.action.*
import vn.deviot.mymqtt.net.mqtt.model.MessageMqtt
import java.io.UnsupportedEncodingException

class MyMqttService : MqttBlueprint() {

    private val TAG = MyMqttService::class.java.simpleName

    private val binder = LocalBinder()

    private var hasConnected = false
    private var hasSubscribe = false

    private var client: MqttAndroidClient? = null

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MyMqttService = this@MyMqttService
    }

    override fun connectUri(url: String, port: Int, username: String?, password: String?) {
        val clientId = MqttClient.generateClientId()
        val uri = "tcp://$url:$port"
        /* Connect MQTT version 3.1 */
        val mqttOption = MqttConnectOptions().apply {
            mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1
        }

        client = MqttAndroidClient(
            applicationContext,
            uri,
            clientId
        ).apply {
            setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    hasConnected = false
                    sendActionConnectLost(cause?.message)
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    hasConnected = isConnected
                    sendActionMessageArrived(topic, message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    hasConnected = isConnected
                    sendActionDeliveryCompleted(token)
                }
            })
        }

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val token = client?.connect(mqttOption)
                token?.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        hasConnected = true
                        val intent = Intent(MqttActionKey.ActionDeliveryComplete.action).apply {
                            putExtra(
                                MqttActionKey.ActionDeliveryComplete.name,
                                asyncActionToken?.isComplete ?: false
                            )
                        }
                        sendBroadcast(intent)
                    }

                    override fun onFailure(
                        asyncActionToken: IMqttToken?,
                        exception: Throwable?
                    ) {
                        hasConnected = false
                        sendActionError(exception?.message)
                    }
                }
            } catch (exception: Exception) {
                sendActionError(exception.message)
            }
        }
    }

    override fun publish(topic: String, content: String, remain: Boolean) {
        if (hasConnected) {
            val encodedPayload: ByteArray?
            try {
                encodedPayload = content.toByteArray(charset("UTF-8"))
                val message = MqttMessage(encodedPayload)
                message.isRetained = remain
                client?.publish(topic, message)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                sendActionError(e.message)
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e.message)
            }
        } else {
            sendActionNoConnect(false)
        }
    }

    override fun subscribe(topic: String, qos: Int) {
        if (hasConnected) {

            /* Subscribe */try {
                val subToken = client?.subscribe(topic, qos)
                subToken?.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        // The message was published
                        hasSubscribe = true
                        sendActionSubscribeSuccess()
                    }

                    override fun onFailure(
                        asyncActionToken: IMqttToken,
                        exception: Throwable
                    ) {
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards
                        hasSubscribe = false
                        sendActionError(exception.message)
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e.message)
            }
        } else {
            sendActionNoConnect(false)
        }
    }

    override fun unSubscribe(topic: String) {
        if (hasSubscribe) {
            try {
                client?.unsubscribe(topic)?.apply {
                    actionCallback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken) {
                            // The subscription could successfully be removed from the client
                            hasSubscribe = false
                            sendActionUnSubscribeSuccess()
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken,
                            exception: Throwable
                        ) {
                            // some error occurred, this is very unlikely as even if the client
                            // did not had a subscription to the topic the unsubscribe action
                            // will be successfully
                            hasSubscribe = true
                            sendActionError(exception.message)
                        }
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }

    override fun disconnect() {
        if (hasConnected) {
            try {
                client?.disconnect()?.apply {
                    actionCallback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken) {
                            // we are now successfully disconnected
                            hasConnected = false
                            hasSubscribe = false
                            sendActionDisconnected()
                            sendActionNoConnect(false)
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken,
                            exception: Throwable
                        ) {
                            // something went wrong, but probably we are disconnected anyway
                            sendActionError(exception.message)
                        }
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e.message)
            }
        } else {
            sendActionNoConnect(false)
        }
    }

    private fun sendActionNoConnect(isConnected: Boolean = false) {
        val intent = Intent(MqttActionKey.ActionNoConnect.action).apply {
            putExtra(MqttActionKey.ActionNoConnect.name, isConnected)
        }
        sendBroadcast(intent)
    }

    private fun extractMqttMessage(topic: String? = null, message: MqttMessage?): MessageMqtt {
        return MessageMqtt(
            topic,
            message?.payload,
            message?.qos,
            message?.id,
            message?.isRetained,
            message?.isDuplicate
        )
    }

    private fun sendActionError(message: String?) {
        val intent = Intent(MqttActionKey.ActionError.action).apply {
            putExtra(MqttActionKey.ActionError.name, message)
        }
        sendBroadcast(intent)
    }

    private fun sendActionConnectLost(cause: String?) {
        val intent = Intent(MqttActionKey.ActionConnectLost.action).apply {
            putExtra(MqttActionKey.ActionConnectLost.name, cause)
        }
        sendBroadcast(intent)
    }

    private fun sendActionSubscribeSuccess() {
        val intent = Intent(MqttActionKey.ActionSubscribeSuccess.action)
        sendBroadcast(intent)
    }

    private fun sendActionUnSubscribeSuccess() {
        val intent = Intent(MqttActionKey.ActionUnSubscribeSuccess.action)
        sendBroadcast(intent)
    }

    private fun sendActionDisconnected() {
        val intent = Intent(MqttActionKey.ActionDisconnected.action)
        sendBroadcast(intent)
    }

    private fun sendActionMessageArrived(
        topic: String?,
        message: MqttMessage?
    ) {
        val intent = Intent(MqttActionKey.ActionReceivedMessage.action).apply {
            putExtra(
                MqttActionKey.ActionReceivedMessage.name,
                extractMqttMessage(
                    topic = topic,
                    message = message
                )
            )
        }
        sendBroadcast(intent)
    }

    private fun sendActionDeliveryCompleted(
        token: IMqttDeliveryToken?
    ) {
        val intent = Intent(MqttActionKey.ActionDeliveryComplete.action).apply {
            putExtra(
                MqttActionKey.ActionDeliveryComplete.name,
                extractMqttMessage(message = token?.message)
            )
        }
        sendBroadcast(intent)
    }
}
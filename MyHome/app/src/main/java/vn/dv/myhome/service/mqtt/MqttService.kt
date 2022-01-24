package vn.dv.myhome.service.mqtt

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import vn.dv.myhome.utils.Constants
import java.io.UnsupportedEncodingException

class MqttService : BaseMqttService(), IOnMqttSendAction {

    private val TAG = MqttService::class.java.simpleName

    private val binder = MqttBinder()

    override fun onBind(intent: Intent?): IBinder = binder

    inner class MqttBinder : Binder() {
        fun getMqttService(): MqttService = this@MqttService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    // mqtt
    private var client: MqttAndroidClient? = null

    private var hasConnected = false
    private var hasSubscribe = false

    override fun connectUriMqtt(url: String, port: Int, username: String?, password: String?) {
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
                    sendActionConnectLost(cause)
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    hasConnected = isConnected
                    sendActionArrivedMessage(topic, message)
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
                        sendActionConnectSuccess(asyncActionToken)
                    }

                    override fun onFailure(
                        asyncActionToken: IMqttToken?,
                        exception: Throwable?
                    ) {
                        hasConnected = false
                        sendActionError(exception)
                    }
                }
            } catch (throwable: Throwable) {
                sendActionError(throwable)
            }
        }
    }

    override fun publishMqtt(
        topic: String,
        content: String,
        remain: Boolean,
        qos: Int
    ) {
        if (hasConnected) {
            val encodedPayload: ByteArray?
            try {
                encodedPayload = content.toByteArray(charset("UTF-8"))
                val message = MqttMessage(encodedPayload)
                message.isRetained = remain
                message.qos = qos
                client?.publish(topic, message)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                sendActionError(e)
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e)
            }
        } else {
            sendActionNoConnected()
        }
    }

    override fun subscribeMqtt(topic: String, qos: Int) {
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
                        exception: Throwable?
                    ) {
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards
                        hasSubscribe = false
                        sendActionError(exception)
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e)
            }
        } else {
            sendActionNoConnected()
        }
    }

    override fun unSubscribeMqtt(topic: String) {
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
                            exception: Throwable?
                        ) {
                            // some error occurred, this is very unlikely as even if the client
                            // did not had a subscription to the topic the unsubscribe action
                            // will be successfully
                            hasSubscribe = true
                            sendActionError(exception)
                        }
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }

    override fun disconnectMqtt() {
        if (hasConnected) {
            try {
                client?.disconnect()?.apply {
                    actionCallback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken) {
                            // we are now successfully disconnected
                            hasConnected = false
                            hasSubscribe = false
                            sendActionDisconnected()
                            sendActionNoConnected()
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken,
                            exception: Throwable
                        ) {
                            // something went wrong, but probably we are disconnected anyway
                            sendActionError(exception)
                        }
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
                sendActionError(e)
            }
        } else {
            sendActionNoConnected()
        }
    }

    // send to broadcast
    override fun sendActionConnectSuccess(asyncActionToken: IMqttToken?) {
        val intent = Intent(MqttActionKey.ConnectSuccess.name).apply {
            putExtra(MqttActionKey.ConnectSuccess.name, asyncActionToken?.toString())
        }
        sendBroadcast(intent)
    }

    override fun sendActionConnectLost(throwable: Throwable?) {
        val intent = Intent(MqttActionKey.ConnectLost.name).apply {
            putExtra(MqttActionKey.ConnectLost.name, throwable?.message)
        }
        sendBroadcast(intent)
    }

    override fun sendActionArrivedMessage(topic: String?, message: MqttMessage?) {
        val intent = Intent(MqttActionKey.ArrivedMessage.name).apply {
            putExtra(MqttActionKey.ArrivedMessage.name + Constants.TOPIC, topic)
            putExtra(MqttActionKey.ArrivedMessage.name + Constants.MESSAGE, message.toString())
        }
        sendBroadcast(intent)
    }

    override fun sendActionDeliveryCompleted(token: IMqttDeliveryToken?) {
        val intent = Intent(MqttActionKey.DeliveryCompleted.name).apply {
            putExtra(MqttActionKey.DeliveryCompleted.name, token.toString())
        }
        sendBroadcast(intent)
    }

    override fun sendActionSubscribeSuccess() {
        val intent = Intent(MqttActionKey.SubscribeSuccess.name)
        sendBroadcast(intent)
    }

    override fun sendActionUnSubscribeSuccess() {
        val intent = Intent(MqttActionKey.UnSubscribeSuccess.name)
        sendBroadcast(intent)
    }

    override fun sendActionError(throwable: Throwable?) {
        val intent = Intent(MqttActionKey.Error.name).apply {
            putExtra(MqttActionKey.Error.name, throwable?.message)
        }
        sendBroadcast(intent)
    }

    override fun sendActionNoConnected() {
        val intent = Intent(MqttActionKey.NoConnected.name)
        sendBroadcast(intent)
    }

    override fun sendActionDisconnected() {
        val intent = Intent(MqttActionKey.Disconnected.name)
        sendBroadcast(intent)
    }
}
package vn.dv.myhome

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.broadcast.MqttBroadcast
import vn.dv.myhome.service.mqtt.MqttActionKey
import vn.dv.myhome.service.mqtt.MqttService

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val mqttBroadcast: MqttBroadcast by lazy { MqttBroadcast() }

    private var mBound = false
    private lateinit var mqttService: MqttService

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MqttService.MqttBinder
            mqttService = binder.getMqttService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    @BindView(R.id.tv)
    lateinit var tv: TextView

    override fun onStart() {
        super.onStart()
        val intentFilter: IntentFilter = IntentFilter().apply {
            MqttActionKey.values().toList().forEach {
                addAction(it.name)
            }
        }
        registerReceiver(mqttBroadcast, intentFilter)

        val intent = Intent(this, MqttService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initListener()

        tv.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (mBound) {
                    mqttService.connectUriMqtt(
                        url = "test.mosquitto.org",
                        port = 1883
                    )
                    delay(2000)

                    mqttService.subscribeMqtt("Duc")
                }
            }
        }

    }

    private fun initListener() {
        mqttBroadcast.addOnMqttListener(object : IMqttBroadcastSendData {
            override fun onConnectSuccess(asyncActionToken: String?) {
                Log.e(TAG, "onConnectSuccess: $asyncActionToken")
            }

            override fun onConnectLost(message: String?) {
                Log.e(TAG, "onConnectLost: $message")
            }

            override fun onArrivedMessage(topic: String?, message: String?) {
                Log.e(TAG, "onArrivedMessage: $topic - $message")
            }

            override fun onDeliveryCompleted(token: String?) {
                Log.e(TAG, "onDeliveryCompleted: $token")
            }

            override fun onSubscribeSuccess() {
                Log.e(TAG, "onSubscribeSuccess: ")
            }

            override fun onUnSubscribeSuccess() {
                Log.e(TAG, "onUnSubscribeSuccess: ")
            }

            override fun onError(message: String?) {
                Log.e(TAG, "onError: $message")
            }

            override fun onNoConnected() {
                Log.e(TAG, "onNoConnected: ")
            }

            override fun onDisconnected() {
                Log.e(TAG, "onDisconnected: ")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mqttBroadcast)
    }
}
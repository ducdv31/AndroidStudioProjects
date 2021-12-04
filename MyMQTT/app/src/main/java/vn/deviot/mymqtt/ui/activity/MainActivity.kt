package vn.deviot.mymqtt.ui.activity

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import vn.deviot.mymqtt.R
import vn.deviot.mymqtt.broadcast.MyBroadcast
import vn.deviot.mymqtt.broadcast.MyMqttListener
import vn.deviot.mymqtt.constans.EMPTY
import vn.deviot.mymqtt.net.mqtt.MyMqttService
import vn.deviot.mymqtt.net.mqtt.action.MqttActionKey
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    @BindView(R.id.start_service)
    lateinit var btnStart: View

    @BindView(R.id.stop_mqtt)
    lateinit var disconnectMqtt: View

    @BindView(R.id.subscribe)
    lateinit var subscribe: View

    @BindView(R.id.publish)
    lateinit var publish: View

    private val myBroadcast: MyBroadcast by lazy { MyBroadcast() }

    private lateinit var mService: MyMqttService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MyMqttService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter().apply {
            MqttActionKey.values().toList().forEach {
                addAction(it.action)
            }
        }
        registerReceiver(myBroadcast, intentFilter)

        /* start service */
        val intent = Intent(this, MyMqttService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        btnStart.setOnClickListener {
            if (mBound) {
                mService.connectUri("test.mosquitto.org", 1883)
            }
        }

        subscribe.setOnClickListener {
            if (mBound) {
                mService.subscribe("duc")
            }
        }

        disconnectMqtt.setOnClickListener {
            if (mBound) {
                mService.disconnect()
            }
        }

        publish.setOnClickListener {
            if (mBound) {
                mService.publish("duc", "Hi Duc ${Random.nextInt()}")
            }
        }

        myBroadcast.addOnMqttListener(object : MyMqttListener {
            override fun onConnectionLost(t: String?) {
                showToast(t ?: EMPTY)
            }

            override fun onServerConnected(status: Boolean) {
            }

            override fun notifyConnected(notify: Boolean) {
            }

            override fun onSubscribe(subscribe: Boolean) {
                if (subscribe) {
                    showToast("Subscribe Success")
                }
            }

            override fun onMessageArrived(topic: String?, message: String?) {
                showToast("$topic : $message")
            }

            override fun onDeliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken?) {
                showToast("Connected")
            }

            override fun onUnsubscribe(unsubscribe: Boolean) {
            }

            override fun onDisconnect() {
                showToast("Disconnected")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        mBound = false
        unregisterReceiver(myBroadcast)
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
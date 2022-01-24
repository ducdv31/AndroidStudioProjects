package vn.dv.myhome

import android.content.*
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.broadcast.MqttBroadcast
import vn.dv.myhome.service.mqtt.MqttActionKey
import vn.dv.myhome.service.mqtt.MqttService
import vn.dv.myhome.utils.Constants
import vn.dv.myhome.utils.bus.GlobalBus
import vn.dv.myhome.view.activity.configmqtt.ConfigMqttActivity

abstract class BaseActivity : AppCompatActivity() {

    private val mqttBroadcast: MqttBroadcast by lazy { MqttBroadcast() }

    private var mBound = false
    private lateinit var mqttService: MqttService

    private var btnBack: View? = null

    @BindView(R.id.title_tool_bar)
    lateinit var titleToolBarBack: AppCompatTextView

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

    abstract fun initVar()

    abstract fun initListener()

    abstract fun requestData()

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
        GlobalBus.getBus().register(this)
        initVar()
        initListener()
        requestData()
        btnBack = findViewById(R.id.btn_back_tool_bar)

        btnBack?.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(mqttBroadcast)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        GlobalBus.getBus().unregister(this)
    }

    protected fun setTitleHeader(title: String) {
        titleToolBarBack.text = title
    }

    protected fun bindMqttService(iMqttBroadcastSendData: IMqttBroadcastSendData) {
        val intentFilter: IntentFilter = IntentFilter().apply {
            MqttActionKey.values().toList().forEach {
                addAction(it.name)
            }
        }
        registerReceiver(mqttBroadcast, intentFilter)

        val intent = Intent(this, MqttService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        mqttBroadcast.addOnMqttListener(iMqttBroadcastSendData)
    }

    protected fun connectMqttService(
        url: String,
        port: Int,
        username: String? = null,
        password: String? = null
    ) {
        if (mBound) {
            mqttService.connectUriMqtt(
                url = url,
                port = port
            )
        }
    }

    fun publishTopic(
        topic: String,
        content: String,
        isRemain: Boolean = false,
        qos: Int = Constants.ZERO
    ) {
        if (mBound) {
            mqttService.publishMqtt(
                topic,
                content,
                isRemain,
                qos
            )
        }
    }

    fun subscribeTopic(
        topic: String,
        qos: Int = Constants.ZERO
    ) {
        if (mBound) {
            mqttService.subscribeMqtt(topic, qos)
        }
    }

    fun unSubscribe(topic: String) {
        if (mBound) {
            mqttService.unSubscribeMqtt(topic)
        }
    }

    fun disconnectMqtt() {
        if (mBound) {
            mqttService.disconnectMqtt()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun openConfigMqttScreen() {
        startActivity(Intent(this, ConfigMqttActivity::class.java))
    }
}
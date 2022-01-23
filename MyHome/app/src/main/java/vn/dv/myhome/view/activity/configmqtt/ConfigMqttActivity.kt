package vn.dv.myhome.view.activity.configmqtt

import android.os.Bundle
import android.util.Log
import android.view.View
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.R
import vn.dv.myhome.broadcast.IMqttBroadcastSendData

class ConfigMqttActivity : BaseActivity() {

    private val TAG = ConfigMqttActivity::class.java.simpleName

    @BindView(R.id.root_view)
    lateinit var rootView: View

    @BindView(R.id.edtHost)
    lateinit var edtHost: TextInputEditText

    @BindView(R.id.edtPort)
    lateinit var edtPort: TextInputEditText

    @BindView(R.id.btn_connect)
    lateinit var btnConnect: View

    override fun onStart() {
        super.onStart()
        bindMqttService(object : IMqttBroadcastSendData {
            override fun onConnectSuccess(asyncActionToken: String?) {
                Log.e(TAG, "onConnectSuccess: $asyncActionToken")
                asyncActionToken?.let { showToast(it) }
            }

            override fun onConnectLost(message: String?) {
                Log.e(TAG, "onConnectLost: $message")
                message?.let { showToast(it) }
            }

            override fun onArrivedMessage(topic: String?, message: String?) {
                Log.e(TAG, "onArrivedMessage: $topic - $message")
                if (topic != null && message != null) {
                    showToast("Topic: $topic - Message: $message")
                }
            }

            override fun onDeliveryCompleted(token: String?) {
                Log.e(TAG, "onDeliveryCompleted: $token")
                if (token != null) {
                    showToast(token)
                }
            }

            override fun onSubscribeSuccess() {
                Log.e(TAG, "onSubscribeSuccess: ")
                showToast("onSubscribeSuccess")
            }

            override fun onUnSubscribeSuccess() {
                Log.e(TAG, "onUnSubscribeSuccess: ")
                showToast("onUnSubscribeSuccess")
            }

            override fun onError(message: String?) {
                Log.e(TAG, "onError: $message")
                showToast("Error: $message")
            }

            override fun onNoConnected() {
                Log.e(TAG, "onNoConnected: ")
                showToast("onNoConnected")
            }

            override fun onDisconnected() {
                Log.e(TAG, "onDisconnected: ")
                showToast("onDisconnected")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_mqtt)
    }

    override fun onResume() {
        super.onResume()
        rootView.requestFocus()
    }

    override fun initVar() {
    }

    override fun initListener() {
        btnConnect.setOnClickListener {
            connectMqttService(
                edtHost.text.toString(),
                edtPort.text.toString().trim().toInt()
            )
        }
    }

    override fun requestData() {
    }
}
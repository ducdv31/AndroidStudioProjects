package vn.dv.myhome.view.activity.configmqtt

import android.os.Bundle
import android.view.View
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.R
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.data.local.datastore.DataStoreManager
import vn.dv.myhome.utils.bus.EventBus
import vn.dv.myhome.utils.bus.GlobalBus
import vn.dv.myhome.view.activity.main.model.SubscribeDataModel
import javax.inject.Inject

@AndroidEntryPoint
class ConfigMqttActivity : BaseActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

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
                showSnackBar(rootView, getString(R.string.connect_success))
            }

            override fun onConnectLost(message: String?) {
                message?.let {
                    showSnackBar(
                        rootView,
                        "${getString(R.string.connection_lost)}: $message"
                    )
                }
            }

            override fun onArrivedMessage(topic: String?, message: String?) {
                if (topic != null && message != null) {
                    val data = SubscribeDataModel(
                        topic,
                        message
                    )

                    val subscribeEvent = EventBus.SubscribeDataEvent.apply {
                        setData(data)
                    }

                    GlobalBus.getBus().post(subscribeEvent)
                }
            }

            override fun onDeliveryCompleted(token: String?) {
                if (token != null) {
                    showSnackBar(rootView, getString(R.string.sent))
                }
            }

            override fun onSubscribeSuccess() {
                showSnackBar(rootView, getString(R.string.subscribe_success))
            }

            override fun onUnSubscribeSuccess() {
                showSnackBar(rootView, getString(R.string.un_subscribe_success))
            }

            override fun onError(message: String?) {
                showSnackBar(rootView, "${getString(R.string.error)}: $message")
            }

            override fun onNoConnected() {
                showToast(getString(R.string.no_connection))
                openConfigMqttScreen()
            }

            override fun onDisconnected() {
                showSnackBar(rootView, getString(R.string.disconnected))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_mqtt)
        setTitleHeader(getString(R.string.config_mqtt))
        CoroutineScope(Dispatchers.Main).launch {
            edtHost.setText(dataStoreManager.hostDataFlow.first())
            edtHost.setSelection(edtHost.length())
            edtPort.setText(dataStoreManager.portDataFlow.first().toString())
        }
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
            saveConfigToLocal(edtHost.text.toString(), edtPort.text.toString().trim().toInt())
        }
    }

    override fun requestData() {
    }

    private fun saveConfigToLocal(host: String, port: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            dataStoreManager.setHostData(host)
            dataStoreManager.setPortData(port)
        }
    }
}
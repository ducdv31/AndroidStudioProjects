package vn.dv.myhome.view.activity.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.R
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.view.activity.main.fragment.DrawerMainFragment

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout

    private lateinit var fragmentDrawer: DrawerMainFragment

    @BindView(R.id.tv)
    lateinit var tv: View

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
        setContentView(R.layout.activity_main)
    }

    override fun initVar() {
        fragmentDrawer =
            supportFragmentManager.findFragmentById(R.id.fragment_drawer) as DrawerMainFragment
        fragmentDrawer.setUpWithDrawer(drawerLayout)
    }

    override fun initListener() {

        tv.setOnClickListener {
            subscribeTopic("Duc")
        }
    }

    override fun requestData() {
    }
}
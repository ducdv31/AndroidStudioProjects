package vn.dv.myhome.view.activity.main

import android.os.Bundle
import android.util.Log
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.R
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.view.activity.main.adapter.EBottomTabHome
import vn.dv.myhome.view.activity.main.adapter.HomeVpAdapter
import vn.dv.myhome.view.activity.main.drawer.DrawerMainFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout

    @BindView(R.id.viewpager)
    lateinit var viewPager2: ViewPager2

    @BindView(R.id.bottom_nav)
    lateinit var bottomNavigationView: BottomNavigationView

    @Inject
    lateinit var homeVpAdapter: HomeVpAdapter

    private lateinit var fragmentDrawer: DrawerMainFragment

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

        viewPager2.apply {
            adapter = homeVpAdapter
            offscreenPageLimit = EBottomTabHome.values().size
        }
    }

    override fun initListener() {

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                EBottomTabHome.values().toList().forEach {
                    if (position == it.ordinal) {
                        bottomNavigationView.menu.findItem(it.bottomId).isChecked =
                            true
                    }
                }
            }
        })

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            EBottomTabHome.values().toList().forEach {
                if (it.bottomId == menuItem.itemId) {
                    viewPager2.currentItem = it.ordinal
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }

    override fun requestData() {
    }
}
package vn.dv.myhome.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.R
import vn.dv.myhome.broadcast.IMqttBroadcastSendData
import vn.dv.myhome.data.local.datastore.DataStoreManager
import vn.dv.myhome.utils.bus.EventBus
import vn.dv.myhome.utils.bus.GlobalBus
import vn.dv.myhome.view.activity.main.adapter.EBottomTabHome
import vn.dv.myhome.view.activity.main.adapter.HomeVpAdapter
import vn.dv.myhome.view.activity.main.drawer.DrawerMainFragment
import vn.dv.myhome.view.activity.main.model.SubscribeDataModel
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

    @BindView(R.id.btn_left_menu)
    lateinit var btnLeftMenu: View

    @Inject
    lateinit var homeVpAdapter: HomeVpAdapter

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private lateinit var fragmentDrawer: DrawerMainFragment

    override fun onStart() {
        super.onStart()
        bindMqttService(object : IMqttBroadcastSendData {
            override fun onConnectSuccess(asyncActionToken: String?) {
                showSnackBar(drawerLayout, getString(R.string.connect_success))
            }

            override fun onConnectLost(message: String?) {
                message?.let {
                    showSnackBar(
                        drawerLayout,
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
                    showSnackBar(drawerLayout, getString(R.string.sent))
                }
            }

            override fun onSubscribeSuccess() {
                showSnackBar(drawerLayout, getString(R.string.subscribe_success))
            }

            override fun onUnSubscribeSuccess() {
                showSnackBar(drawerLayout, getString(R.string.un_subscribe_success))
            }

            override fun onError(message: String?) {
                showSnackBar(drawerLayout, "${getString(R.string.error)}: $message")
            }

            override fun onNoConnected() {
                showToast(getString(R.string.no_connection))
                openConfigMqttScreen()
            }

            override fun onDisconnected() {
                showSnackBar(drawerLayout, getString(R.string.disconnected))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitleHeader(getString(R.string.home))
    }

    override fun initVar() {
        fragmentDrawer =
            supportFragmentManager.findFragmentById(R.id.fragment_drawer) as DrawerMainFragment
        fragmentDrawer.setUpWithDrawer(drawerLayout)

        viewPager2.apply {
            adapter = homeVpAdapter
            offscreenPageLimit = EBottomTabHome.values().size
            isUserInputEnabled = false
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

        btnLeftMenu.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun requestData() {
    }
}
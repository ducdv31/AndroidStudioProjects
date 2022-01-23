package vn.dv.myhome.view.activity.main.drawer

import android.content.Intent
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import vn.dv.myhome.BaseFragment
import vn.dv.myhome.R
import vn.dv.myhome.view.activity.configmqtt.ConfigMqttActivity

class DrawerMainFragment : BaseFragment(R.layout.fragment_drawer_main) {

    @BindView(R.id.config_mqtt)
    lateinit var configMqtt: View

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var drawerLayout: DrawerLayout? = null

    override fun initVar(view: View) {
    }

    override fun initListener() {
        configMqtt.setOnClickListener {
            closeMenu()
            startActivity(Intent(context, ConfigMqttActivity::class.java))
        }
    }

    override fun requestData() {
    }

    fun setUpWithDrawer(drawerLayout: DrawerLayout) {
        this.drawerLayout = drawerLayout
        actionBarDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle as ActionBarDrawerToggle)
        drawerLayout.post {
            (actionBarDrawerToggle as ActionBarDrawerToggle).syncState()
        }
    }

    private fun closeMenu() {
        drawerLayout?.closeDrawer(GravityCompat.START)
    }

}
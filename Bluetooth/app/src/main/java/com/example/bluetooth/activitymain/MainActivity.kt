package com.example.bluetooth.activitymain

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.fragment.AboutFragment
import com.example.bluetooth.activitymain.fragment.HomeFragment
import com.example.bluetooth.activitymain.fragment.ListDevicesFragment
import com.example.bluetooth.activitymain.model.DataRS
import com.example.bluetooth.dialog.DialogSetting
import com.example.bluetooth.initbluetooth.InitBluetooth

open class MainActivity : AppCompatActivity(), InitBluetooth.IBluetoothListener {

    companion object {
        var devicesItem: MenuItem? = null
    }

    private val TAG_HOME_BACKSTACK = "Add home fragment to backstack"
    var actionBar: ActionBar? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> gotoListDevices()
                else -> {
                }
            }
        }

    lateinit var dialogSetting: DialogSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        actionBar = supportActionBar
        dialogSetting = DialogSetting()

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, HomeFragment())
        fragmentTransition.commit()

        InitBluetooth.startListening(this)
    }

    override fun onRestart() {
        super.onRestart()
        InitBluetooth.startListening(this)
    }

    fun startConnect(bluetoothDevice: BluetoothDevice) {
        InitBluetooth.getInstance().onStartConnect(bluetoothDevice)
    }

    fun getListDevices(): MutableList<BluetoothDevice> {
        return InitBluetooth.getInstance().getListDevices()
    }

    fun sendData(data: Any) {
        InitBluetooth.getInstance().onSendData(data)
    }

    open fun showActionBar(isShow: Boolean) {
        val actionBar: ActionBar? = supportActionBar
        if (isShow) {
            actionBar?.let {
                if (!actionBar.isShowing) {
                    actionBar.show()
                }
            }
        } else {
            actionBar?.let {
                if (actionBar.isShowing) {
                    actionBar.hide()
                }
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    open fun requestOrientation(orientation: Int) {
        requestedOrientation = orientation
    }

    /* other func */
    private fun gotoListDevices() {
        /* check bluetooth && request */
        if (!InitBluetooth.getInstance().isBluetoothEnable()) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            getContent.launch(intent)
        } else {
            /* go to list device */
            val fragmentTransition = supportFragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.frame_main, ListDevicesFragment())
            fragmentTransition.addToBackStack(TAG_HOME_BACKSTACK)
            fragmentTransition.commit()
        }
    }

    fun gotoAboutFragment() {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, AboutFragment())
        fragmentTransition.addToBackStack(TAG_HOME_BACKSTACK)
        fragmentTransition.commit()
    }

    fun gotoFragment(fragment: Fragment, addBackStack: Boolean) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, fragment)
        if (addBackStack) {
            fragmentTransition.addToBackStack(TAG_HOME_BACKSTACK)
        }
        fragmentTransition.commit()
    }

    fun gotoActivity(@NonNull activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        InitBluetooth.getInstance().onCloseSocket()
    }

    open fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        devicesItem = menu!!.findItem(R.id.device_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.device_menu -> gotoListDevices()
            R.id.setting_menu -> dialogSetting.show(supportFragmentManager, "Dialog Setting")
        }

        return false
    }

    override fun onSocketIsConnected(status: Boolean) {
        if (status) {
            showToast("Connected")
            devicesItem!!.setIcon(R.drawable.outline_bluetooth_connected_white_24dp)
        } else {
            showToast("Disconnected")
            devicesItem!!.setIcon(R.drawable.outline_bluetooth_connected_black_24dp)
        }
    }

    override fun onDataReceived(data: Any) {
        HomeFragment.listRS.add(DataRS(data.toString(), false))
        HomeFragment.rcvDataAdapter?.setData(HomeFragment.listRS)
        HomeFragment.recyclerView?.scrollToPosition(HomeFragment.listRS.size - 1)
    }

    override fun onSentData(data: Any) {
        HomeFragment.listRS.add(DataRS(data.toString(), true))
        HomeFragment.rcvDataAdapter?.setData(HomeFragment.listRS)
        HomeFragment.recyclerView?.scrollToPosition(HomeFragment.listRS.size - 1)
    }

    /* hide status bar */
    fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(R.id.frame_main)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView.findViewById(R.id.frame_main)).show(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
        )
    }
    /* *************** */

    /* other function  */
    fun closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        val view = this.currentFocus

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            val manager = this
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
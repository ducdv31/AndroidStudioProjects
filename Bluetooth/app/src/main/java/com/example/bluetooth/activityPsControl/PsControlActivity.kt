package com.example.bluetooth.activityPsControl

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.bluetooth.R
import com.example.bluetooth.activityPsControl.fragment.ControlFragment
import com.example.bluetooth.initbluetooth.InitBluetooth

class PsControlActivity : AppCompatActivity(), InitBluetooth.IBluetoothListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ps_control)
        hideSystemUI(R.id.frame_ps_control)
        showActionBar(false)
        requestOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        InitBluetooth.startListening(this)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_ps_control, ControlFragment())
        fragmentTransition.commit()
    }

    override fun onStart() {
        super.onStart()
        InitBluetooth.startListening(this)
    }

    override fun onRestart() {
        super.onRestart()
        InitBluetooth.startListening(this)
        hideSystemUI(R.id.frame_ps_control)
    }

    /* ********* */

    fun sendData(data: Any) {
        InitBluetooth.getInstance().onSendData(data)
    }

    /* bar */
    private fun showActionBar(isShow: Boolean) {
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
    fun requestOrientation(orientation: Int) {
        requestedOrientation = orientation
    }

    /* hide status bar */
    private fun hideSystemUI(idRootView: Int) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(idRootView)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun showSystemUI(idRootView: Int) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView.findViewById(idRootView)).show(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
        )
    }
    /* *************** */

    fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onSocketIsConnected(status: Boolean) {
        if (!status) {
            showToast("Not connection")
        }
    }

    override fun onDataReceived(data: Any) {
        showToast(data.toString())
    }

    override fun onSentData(data: Any) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
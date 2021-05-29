package com.example.bluetooth.activitymain

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.fragment.HomeFragment
import com.example.bluetooth.activitymain.fragment.ListDevicesFragment
import com.example.bluetooth.initbluetooth.InitBluetooth

class MainActivity : AppCompatActivity(), InitBluetooth.IBluetoothListener {

    companion object {
        const val TAG_HOME_BACKSTACK = "Add home fragment to backstack"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, HomeFragment())
        fragmentTransition.commit()

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

    /* other func */
    fun gotoListDevices() {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, ListDevicesFragment())
        fragmentTransition.addToBackStack(TAG_HOME_BACKSTACK)
        fragmentTransition.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        InitBluetooth.getInstance().onCloseSocket()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    override fun onSocketIsConnected(status: Boolean) {
        showToast(status.toString())
    }

    override fun onDataReceived(data: Any) {
        showToast(data.toString())
    }
}
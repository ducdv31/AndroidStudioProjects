package com.example.bluetooth.activitymain

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.fragment.HomeFragment
import com.example.bluetooth.activitymain.fragment.ListDevicesFragment
import com.example.bluetooth.initbluetooth.InitBluetooth

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_HOME_BACKSTACK = "Add home fragment to backstack"
    }

    lateinit var initBluetooth: InitBluetooth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, HomeFragment())
        fragmentTransition.commit()

        initBt()
    }

    private fun initBt() {
        initBluetooth = InitBluetooth()
    }

    fun startConnect(bluetoothDevice: BluetoothDevice) {
        val thread = Thread {
            initBluetooth.onStartConnect(bluetoothDevice)
        }
        thread.start()
    }

    fun getListDevices(): MutableList<BluetoothDevice> {
        return initBluetooth.getListDevices()
    }

    fun sendData(data: Any) {
        if (initBluetooth.bluetoothSocket != null && initBluetooth.bluetoothSocket?.isConnected!!) {
            initBluetooth.onSendData(data)
        } else {
            initBluetooth.getSocket(initBluetooth.getListDevices()[8])
            initBluetooth.onSendData(data)
        }
    }

    fun gotoListDevices() {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, ListDevicesFragment())
        fragmentTransition.addToBackStack(TAG_HOME_BACKSTACK)
        fragmentTransition.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        initBluetooth.onCloseSocket()
    }
}
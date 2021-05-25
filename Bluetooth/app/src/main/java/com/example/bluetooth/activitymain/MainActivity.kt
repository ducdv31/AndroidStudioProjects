package com.example.bluetooth.activitymain

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.fragment.HomeFragment
import com.example.bluetooth.activitymain.fragment.ListDevicesFragment
import com.example.bluetooth.initbluetooth.InitBluetooth

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_HOME_BACKSTACK = "Add home fragment to backstack"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_main, HomeFragment())
        fragmentTransition.commit()

    }

    fun startConnect(bluetoothDevice: BluetoothDevice) {
        val thread = Thread {
            InitBluetooth.getInstance().onStartConnect(bluetoothDevice)
        }
        thread.start()
    }

    fun getListDevices(): MutableList<BluetoothDevice> {
        return InitBluetooth.getInstance().getListDevices()
    }

    fun sendData(data: Any) {
        if (InitBluetooth.getInstance().bluetoothSocket != null && InitBluetooth.getInstance().bluetoothSocket?.isConnected!!) {
            InitBluetooth.getInstance().onSendData(data)
        } else {
            Toast.makeText(this, "No connect", Toast.LENGTH_SHORT).show()
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
        InitBluetooth.getInstance().onCloseSocket()
    }
}
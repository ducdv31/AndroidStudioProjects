package com.example.bluetooth.activitymain

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.fragment.HomeFragment
import com.example.bluetooth.activitymain.fragment.ListDevicesFragment
import com.example.bluetooth.initbluetooth.InitBluetooth
import kotlinx.coroutines.*

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

    suspend fun startConnect(bluetoothDevice: BluetoothDevice) {
        withContext(Dispatchers.IO) {
            InitBluetooth.getInstance().onStartConnect(bluetoothDevice)
        }
    }

    fun getListDevices(): MutableList<BluetoothDevice> {
        return InitBluetooth.getInstance().getListDevices()
    }

    suspend fun sendData(data: Any) {
        if (InitBluetooth.getInstance().bluetoothSocket != null && InitBluetooth.getInstance().bluetoothSocket?.isConnected!!) {
            withContext(Dispatchers.IO){
                InitBluetooth.getInstance().onSendData(data)
            }
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
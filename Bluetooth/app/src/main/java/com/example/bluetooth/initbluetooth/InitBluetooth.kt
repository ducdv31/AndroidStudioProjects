package com.example.bluetooth.initbluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.bluetooth.activitymain.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.util.*

@Suppress("BlockingMethodInNonBlockingContext")
class InitBluetooth private constructor(): BaseBluetooth() {

    interface IBluetoothListener {
        fun onSocketIsConnected(status: Boolean)
        fun onDataReceived(data: Any)
    }

    companion object {

        private lateinit var iBluetoothListener: IBluetoothListener

        private var instances: InitBluetooth = InitBluetooth()

        fun startListening(activity: Activity) {
            this.iBluetoothListener = activity as MainActivity
        }

        fun getInstance(): InitBluetooth {
            return instances
        }
    }

    override var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    override var bluetoothSocket: BluetoothSocket? = null
    override var bluetoothDevice: BluetoothDevice? = null

    override fun getListDevices(): MutableList<BluetoothDevice> {
        val listDevices: MutableSet<BluetoothDevice> = bluetoothAdapter.bondedDevices
        return listDevices.toMutableList()
    }

    override fun onStartConnect(bluetoothDevice: BluetoothDevice) {
        /* get UUID of client */
        val clientMac: String = bluetoothDevice.toString()
        this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(clientMac)
        val uuid: String = this.bluetoothDevice!!.uuids[0].toString()
        val clientUuid2: UUID = UUID.fromString(uuid)
        CoroutineScope(IO).launch {
            /* start connect */
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(clientUuid2)

            try {
                bluetoothSocket!!.connect()
            } catch (e: IOException) {
                Log.e("InitBluetooth", "onStartConnect: $e")
            } finally {
                withContext(Main) {
                    checkSocketStatus(bluetoothSocket!!.isConnected)
                }
                if (bluetoothSocket!!.isConnected) {
                    onReceived()
                }
            }
        }
    }

    @Suppress("DeferredResultUnused")
    override fun onSendData(data: Any) {
        /* check bluetooth socket */
        if (bluetoothSocket != null && bluetoothSocket?.isConnected!!) {
            CoroutineScope(IO).async {
                val dataSend: ByteArray = data.toString().toByteArray()
                val outputStream = bluetoothSocket!!.outputStream
                outputStream.write(dataSend)
                outputStream.flush()
            }
        } else {
            checkSocketStatus(bluetoothSocket!!.isConnected)
        }

    }

    override suspend fun onReceived() {
        val job = CoroutineScope(Main).async {
            val inputStream: InputStream = bluetoothSocket!!.inputStream
            while (bluetoothSocket!!.isConnected) {
                var b: Int
                val text = StringBuilder()
                withContext(IO) {
                    while ((inputStream.read().also { b = it }) != 13) {
                        val dataReceived = b.toChar().toString()
                        text.append(dataReceived)
                    }
                }
                showData(text.trim())
            }
        }
        job.join()
    }

    override fun onCloseSocket() {
        if (bluetoothDevice != null && bluetoothSocket?.isConnected!!) {
            bluetoothSocket!!.close()
            bluetoothDevice = null
            checkSocketStatus(bluetoothSocket!!.isConnected)
        }
    }

    override fun showData(data: Any) {
        iBluetoothListener.onDataReceived(data)
    }

    override fun checkSocketStatus(isSocketConnected: Boolean) {
        iBluetoothListener.onSocketIsConnected(isSocketConnected)
    }
}
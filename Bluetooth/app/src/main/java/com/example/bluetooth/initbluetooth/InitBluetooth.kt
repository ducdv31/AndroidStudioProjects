package com.example.bluetooth.initbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.util.*

class InitBluetooth private constructor(): BaseBluetooth() {

    companion object {

        private var instances: InitBluetooth = InitBluetooth()

        fun getInstance(): InitBluetooth {
            return instances
        }
    }

    override var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    override var bluetoothSocket: BluetoothSocket? = null
    override var bluetoothDevice: BluetoothDevice? = null

    /* check variable */

    /* ************** */

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

        /* start connect */
        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(clientUuid2)

        try {
            bluetoothSocket!!.connect()
        } catch (e: IOException) {
            Log.e("InitBluetooth", "onStartConnect: $e")
        } finally {
            if (bluetoothSocket!!.isConnected) {
                val job = CoroutineScope(Main).async {
                    onReceived()
                }
            }
        }
        /* create 1 coroutine to listen and send */
    }

    override suspend fun onSendData(data: Any) {
        /* check bluetooth socket */
        if (bluetoothSocket != null && bluetoothSocket?.isConnected!!) {
            if (data is String) {
                withContext(IO){
                    val dataSend: ByteArray = data.toString().toByteArray()
                    val outputStream = bluetoothSocket!!.outputStream
                    outputStream.write(dataSend)
                    outputStream.flush()
                }
            }
        }

    }

    override suspend fun onReceived() {
        withContext(IO){
            Log.e("InitBluetooth", "onReceived: Running listen")
            val inputStream: InputStream = bluetoothSocket!!.inputStream
            while (bluetoothSocket!!.isConnected) {
                var b: Int
                val text = StringBuilder()
                while ((inputStream.read().also { b = it }) != 13) {
                    val dataReceived = b.toChar().toString()
                    text.append(dataReceived)
                }
                Log.e("InitBluetooth", "onReceived: ${text.trim()}")
            }
        }
        /* listen data */
    }

    override fun onCloseSocket() {
        if (bluetoothDevice != null && bluetoothSocket?.isConnected!!) {
            bluetoothSocket!!.close()
            bluetoothDevice = null
        }
    }
}
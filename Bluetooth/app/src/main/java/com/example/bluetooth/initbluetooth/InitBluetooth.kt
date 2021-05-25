package com.example.bluetooth.initbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.util.*

class InitBluetooth private constructor(): BaseBluetooth() {

    companion object {

        var instances: InitBluetooth = InitBluetooth()

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
            Log.e("TAG", "onStartConnect: $e")
        } finally {
            if (bluetoothSocket!!.isConnected) {
                onReceived()
            }
        }
        /* create 1 coroutine to listen and send */
    }

    override fun onSendData(data: Any) {
        /* check bluetooth socket */
        if (bluetoothSocket != null && bluetoothSocket?.isConnected!!) {
            if (data is String) {
                val threadSend = Thread {
                    val dataSend: ByteArray = data.toString().toByteArray()
                    try {
                        val outputStream = bluetoothSocket!!.outputStream
                        outputStream.write(dataSend)
                        outputStream.flush()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                threadSend.start()
            }
        }

    }

    override fun onReceived() {
        /* check bluetooth socket */
        val threadReceived = Thread {
            try {
                Log.e("TAG", "onReceived: Running listen")
                val inputStream: InputStream = bluetoothSocket!!.inputStream
                while (bluetoothSocket!!.isConnected) {
                    var b: Int
                    val text = StringBuilder()
                    while ((inputStream.read().also { b = it }) != 13) {
                        val dataReceived = b.toChar().toString()
                        text.append(dataReceived)
                    }
                    Log.e("TAG", "onReceived: ${text.trim()}")
                }
            } catch (e: IOException) {
                Log.e("Error onReceived", "$e")
            }

        }
        threadReceived.start()
        /* listen data */
    }

    override fun onCloseSocket() {
        if (bluetoothDevice != null && bluetoothSocket?.isConnected!!) {
            bluetoothSocket!!.close()
            bluetoothDevice = null
        }
    }
}
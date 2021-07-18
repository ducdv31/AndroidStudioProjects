package com.example.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler()

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000
    private lateinit var scan: Button
    private lateinit var stop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scan = findViewById(R.id.scan)
        stop = findViewById(R.id.stop)

        scan.setOnClickListener {
            scanLeDevice()
        }

        stop.setOnClickListener {
            stopScan()
        }

    }

    private fun scanLeDevice() {
        bluetoothLeScanner.startScan(leScanCallback)
        Log.e(TAG, "scanLeDevice: ")
    }

    private fun stopScan() {
        bluetoothLeScanner.stopScan(leScanCallback)
        Log.e(TAG, "stopScan: ")
    }

    //    private val leDeviceListAdapter = LeDeviceListAdapter()
    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
//            leDeviceListAdapter.addDevice(result.device)
//            leDeviceListAdapter.notifyDataSetChanged()
            Log.e(TAG, "onScanResult: ${result.device}")
        }
    }
}
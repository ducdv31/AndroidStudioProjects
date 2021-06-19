package com.example.ble

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.ParcelUuid
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    private val SELECT_DEVICE_REQUEST_CODE: Int = 2

    private val deviceManager: CompanionDeviceManager by lazy {
        getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // To skip filters based on names and supported feature flags (UUIDs),
        // omit calls to setNamePattern() and addServiceUuid()
        // respectively, as shown in the following  Bluetooth example.
        val deviceFilter: BluetoothDeviceFilter = BluetoothDeviceFilter.Builder()
            .setNamePattern(Pattern.compile("My device"))
            .addServiceUuid(ParcelUuid(UUID(0x123abcL, -1L)), null)
            .build()

        // The argument provided in setSingleDevice() determines whether a single
        // device name or a list of them appears.
        val pairingRequest: AssociationRequest = AssociationRequest.Builder()
            .addDeviceFilter(deviceFilter)
            .setSingleDevice(true)
            .build()

        // When the app tries to pair with a Bluetooth device, show the
        // corresponding dialog box to the user.
        deviceManager.associate(pairingRequest,
            object : CompanionDeviceManager.Callback() {

                override fun onDeviceFound(chooserLauncher: IntentSender) {
                    startIntentSenderForResult(
                        chooserLauncher,
                        SELECT_DEVICE_REQUEST_CODE, null, 0, 0, 0
                    )
                }

                override fun onFailure(error: CharSequence?) {
                    // Handle the failure.
                }
            }, null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SELECT_DEVICE_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // The user chose to pair the app with a Bluetooth device.
                    val deviceToPair: BluetoothDevice? =
                        data?.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE)
                    deviceToPair?.createBond()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
package com.example.bleexampledoc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertisingSet;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private AdvertisingSet currentAdvertisingSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        example1();
    }

    void example1() {
        BluetoothLeAdvertiser advertiser =
                BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        AdvertisingSetParameters parameters = (new AdvertisingSetParameters.Builder())
                .setLegacyMode(true) // True by default, but set here as a reminder.
                .setConnectable(true)
                .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                .build();

        AdvertiseData data = (new AdvertiseData.Builder()).setIncludeDeviceName(true).build();

        AdvertisingSetCallback callback = new AdvertisingSetCallback() {

            @Override
            public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int txPower, int status) {
                Log.i("LOG_TAG", "onAdvertisingSetStarted(): txPower:" + txPower + " , status: "
                        + status);
                currentAdvertisingSet = advertisingSet;
            }

            @Override
            public void onAdvertisingDataSet(AdvertisingSet advertisingSet, int status) {
                Log.i("LOG_TAG", "onAdvertisingDataSet() :status:" + status);
            }

            @Override
            public void onScanResponseDataSet(AdvertisingSet advertisingSet, int status) {
                Log.i("LOG_TAG", "onScanResponseDataSet(): status:" + status);
            }

            @Override
            public void onAdvertisingSetStopped(AdvertisingSet advertisingSet) {
                Log.i("LOG_TAG", "onAdvertisingSetStopped():");
            }
        };

        advertiser.startAdvertisingSet(parameters, data, null, null, null, callback);

        // After onAdvertisingSetStarted callback is called, you can modify the
        // advertising data and scan response data:
        currentAdvertisingSet.setAdvertisingData(new AdvertiseData.Builder().
                setIncludeDeviceName(true).setIncludeTxPowerLevel(true).build());
        // Wait for onAdvertisingDataSet callback...
        currentAdvertisingSet.setScanResponseData(new
                AdvertiseData.Builder().addServiceUuid(new ParcelUuid(UUID.randomUUID())).build());
        // Wait for onScanResponseDataSet callback...

        // When done with the advertising:
        advertiser.stopAdvertisingSet(callback);
    }

    void example2() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeAdvertiser advertiser =
                BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        // Check if all features are supported
        if (!adapter.isLe2MPhySupported()) {
            Log.e("LOG_TAG", "2M PHY not supported!");
            return;
        }
        if (!adapter.isLeExtendedAdvertisingSupported()) {
            Log.e("LOG_TAG", "LE Extended Advertising not supported!");
            return;
        }

        int maxDataLength = adapter.getLeMaximumAdvertisingDataLength();

        AdvertisingSetParameters.Builder parameters = (new AdvertisingSetParameters.Builder())
                .setLegacyMode(false)
                .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                .setPrimaryPhy(BluetoothDevice.PHY_LE_1M)
                .setSecondaryPhy(BluetoothDevice.PHY_LE_2M);

        AdvertiseData data = (new AdvertiseData.Builder()).addServiceData(new
                        ParcelUuid(UUID.randomUUID()),
                "You should be able to fit large amounts of data up to maxDataLength. This goes up to 1650bytes.For legacy advertising this would not work ".getBytes()).build();

        AdvertisingSetCallback callback = new AdvertisingSetCallback() {
            @Override
            public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int txPower, int status) {
                Log.i("LOG_TAG", "onAdvertisingSetStarted(): txPower:" + txPower + " , status: "
                        + status);
                currentAdvertisingSet = advertisingSet;
            }

            @Override
            public void onAdvertisingSetStopped(AdvertisingSet advertisingSet) {
                Log.i("LOG_TAG", "onAdvertisingSetStopped():");
            }
        };

        advertiser.startAdvertisingSet(parameters.build(), data, null, null, null, callback);

        // After the set starts, you can modify the data and parameters of currentAdvertisingSet.
        currentAdvertisingSet.setAdvertisingData((new
                AdvertiseData.Builder()).addServiceData(new ParcelUuid(UUID.randomUUID()),
                "Without disabling the advertiser first, you can set the data, if new data is less than 251 bytes long".getBytes()).build());

        // Wait for onAdvertisingDataSet callback...

        // Can also stop and restart the advertising
        currentAdvertisingSet.enableAdvertising(false, 0, 0);
        // Wait for onAdvertisingEnabled callback...
        currentAdvertisingSet.enableAdvertising(true, 0, 0);
        // Wait for onAdvertisingEnabled callback...

        // Or modify the parameters - i.e. lower the tx power
        currentAdvertisingSet.enableAdvertising(false, 0, 0);
        // Wait for onAdvertisingEnabled callback...
        currentAdvertisingSet.setAdvertisingParameters(parameters.setTxPowerLevel
                (AdvertisingSetParameters.TX_POWER_LOW).build());
        // Wait for onAdvertisingParametersUpdated callback...
        currentAdvertisingSet.enableAdvertising(true, 0, 0);
        // Wait for onAdvertisingEnabled callback...

        // When done with the advertising:
        advertiser.stopAdvertisingSet(callback);
    }
}
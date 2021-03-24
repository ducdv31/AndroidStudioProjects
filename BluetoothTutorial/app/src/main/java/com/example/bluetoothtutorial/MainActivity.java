package com.example.bluetoothtutorial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUIRE_ENABLE_BT = 0;
    private static final int REQUIRE_DISCOVER_BT = 1;

    TextView mStatus, mPaired;
    ImageView mBlue;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUIRE_ENABLE_BT:
                if (requestCode == RESULT_OK) {
                    mBlue.setImageResource(R.drawable.ic_launcher_background);
                    Toast.makeText(this, "BLT is on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "could not on BLT", Toast.LENGTH_SHORT).show();

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatus = findViewById(R.id.status);
        mPaired = findViewById(R.id.paredDevices);
        mBlue = findViewById(R.id.bluetooth);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.discoverBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);


        // Adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // check bluetooth
        if (bluetoothAdapter == null) {
            mStatus.setText("Bluetooth off");
        } else {
            mStatus.setText("Bluetooth on");
        }

        // Set img according to bluetooth

        if (bluetoothAdapter.isEnabled()) {
            mBlue.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            mBlue.setImageResource(R.drawable.ic_launcher_background);
        }

        // On bluetooth
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainActivity.this, "Turning On BLT", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUIRE_ENABLE_BT);
                } else {
                    Toast.makeText(MainActivity.this, "BLT is ON", Toast.LENGTH_SHORT).show();

                }
            }
        });
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(MainActivity.this, "Making your Devices Discoverable", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUIRE_DISCOVER_BT);
                }
            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                    Toast.makeText(MainActivity.this, "Turning off BLT", Toast.LENGTH_SHORT).show();
                    mBlue.setImageResource(R.drawable.ic_launcher_background);
                }else {
                    Toast.makeText(MainActivity.this, "BLT is OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (bluetoothAdapter.isEnabled()){
                mPaired.setText("Paired devices");
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice bluetoothDevice : devices){
                    mPaired.append("\nDevices " + bluetoothDevice.getName()+ " , "+bluetoothDevice);
                }
            }
            else {
                // BLT off cant get paired devices
                Toast.makeText(MainActivity.this, "Turn of BLT to get Paired devices", Toast.LENGTH_SHORT).show();

            }
            }
        });

    }
}
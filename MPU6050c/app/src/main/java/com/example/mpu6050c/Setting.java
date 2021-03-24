package com.example.mpu6050c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    Switch DHT11_Switch, MPU6050_Switch;
    TableLayout MPU_Table;
    Boolean Switch_MPU = true, Switch_DHT = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MPU6050_Switch = (Switch) findViewById(R.id.Switch_MPU);
        DHT11_Switch = (Switch) findViewById(R.id.Switch_DHT11);
        MPU_Table = (TableLayout) findViewById(R.id.MPU_Table);
        MPU6050_Switch.setChecked(Switch_MPU);
        DHT11_Switch.setChecked(Switch_DHT);
        MPU6050_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch_MPU = MPU6050_Switch.isChecked();
                Toast.makeText(Setting.this, MPU6050_Switch.isChecked() ? "Turned on MPU6050" : "Turned off MPU6050", Toast.LENGTH_SHORT).show();

            }
        });
        DHT11_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch_DHT = DHT11_Switch.isChecked();
                Toast.makeText(Setting.this, DHT11_Switch.isChecked() ? "Turned on DHT11" : "Turned off DHT11", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("Switch_MPU", MPU6050_Switch.isChecked());
        outState.putBoolean("Switch_DHT11", DHT11_Switch.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Switch_MPU = savedInstanceState.getBoolean("Switch_MPU");
        Switch_DHT = savedInstanceState.getBoolean("Switch_DHT");
        DHT11_Switch.setChecked(Switch_DHT);
        MPU6050_Switch.setChecked(Switch_MPU);
    }
}

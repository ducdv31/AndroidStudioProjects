package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.sharedpreference.sharedpreference.DataLocalManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!DataLocalManager.getFirstInstall()){
            Toast.makeText(this, "First Install", Toast.LENGTH_SHORT).show();
            DataLocalManager.setFirstInstall(true);
        }
    }
}
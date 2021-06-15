package com.example.foregroundservices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText_data_intent);
        start = findViewById(R.id.start_services);
        stop = findViewById(R.id.stop_services);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(MainActivity.this::clickStartServices).start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStopServices();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: ");
    }

    private void clickStopServices() {
        Intent intent = new Intent(this, MyServices.class);
        stopService(intent);
    }

    private void clickStartServices() {
        Intent intent = new Intent(this, MyServices.class);
        intent.putExtra("Key Data intent", editText.getText().toString().trim());
        startService(intent);
    }
}
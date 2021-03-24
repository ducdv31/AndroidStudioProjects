package com.example.mpu6050c;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.RandomAccess;

public class SetDatabaseActivities extends AppCompatActivity {
    Button submitbt;
    EditText editText;
    double AcX, AcY, AcZ, GyX, GyY, GyZ;
    static DatabaseReference VDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_database_activities);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("SET DATABASE");
        submitbt = (Button)findViewById(R.id.submitBt);
        editText = (EditText)findViewById(R.id.editTextNumber);
        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String text = editText.getText().toString();
            setDatabase setText = new setDatabase("TextInput/line1",text);
            setText.setText();
            }
        });
    }

}


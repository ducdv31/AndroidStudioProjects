package com.example.nrfcontrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.drawable.icons8_lamp);
//        actionBar.setDisplayUseLogoEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Button on = findViewById(R.id.on_btn);
        Button off = findViewById(R.id.off_btn);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("LED").setValue("1");
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("LED").setValue("0");
            }
        });
    }
}
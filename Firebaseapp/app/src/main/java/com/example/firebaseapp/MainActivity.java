package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mData;
    TextView txtName;
    DatabaseReference Data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = (TextView) findViewById(R.id.txtName);
        Data1 = FirebaseDatabase.getInstance().getReference();
        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        DatabaseReference duc = database.getReference("Name");
        duc.setValue("Đặng Văn Đức");
        //
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Tuoi").setValue("21");
        mData.child("Address").setValue("Ha Noi");
        //Doi tuong sinh vien
        sinhvien sv = new sinhvien("Duc", "1999");
        mData.child("sinhvien").setValue(sv);
        //Map
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        myMap.put("Xedap", 1);
        myMap.put("Oto", 1);
        mData.child("Phuong tien").setValue(myMap);
        //Push
//        sinhvien Sinhvien = new sinhvien("Nga","1999");
//        mData.child("DanhsachSV").push().setValue(Sinhvien);
        // Bat su kien hoan thanh khi setValue
//        Data1.child("Check").setValue("Firebase test connect", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                if (databaseError == null) {
//                    Toast.makeText(MainActivity.this, "Luu thanh cong", Toast.LENGTH_SHORT).show();// Toast In len thong bao mini bottom
//                } else {
//                    Toast.makeText(MainActivity.this, "Khong luu duoc", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        Data1.child("Name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                txtName.setText(dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
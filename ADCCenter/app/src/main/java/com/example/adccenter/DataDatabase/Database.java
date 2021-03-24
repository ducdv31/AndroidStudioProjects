package com.example.adccenter.DataDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Class Parent
public class Database {
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
}

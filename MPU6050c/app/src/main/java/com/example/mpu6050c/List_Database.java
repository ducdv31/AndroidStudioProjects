package com.example.mpu6050c;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class List_Database extends AppCompatActivity {
    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayAdapter<String> myArrayAdapter;
    public static final String TAG = List_Database.class.getSimpleName();
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__database);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Database");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myListView = (ListView) findViewById(R.id.List_View);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);
        final getChildDatabase Time = new getChildDatabase(myListView, myArrayList, myArrayAdapter, "");
        Time.getChildList();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Time.myWrapper(myArrayList.get(position));
                Toast.makeText(List_Database.this, myArrayList.get(position), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Database Selected " + myArrayList.get(position));
            }
        });


    }
}
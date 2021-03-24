package com.example.myhouse.DataDatabase;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

class GetChildDatabase extends Database {
    private String child;
    private ListView myListView;
    private ArrayList<String> myArrayList; // = new ArrayList<>();
    private ArrayAdapter<String> myArrayAdapter; // new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrayList);

    GetChildDatabase(ListView myListView, ArrayList<String> myArrayList, ArrayAdapter<String> myArrayAdapter, String child) {
        this.child = child;
        this.myListView = myListView;
        this.myArrayList = myArrayList;
        this.myArrayAdapter = myArrayAdapter;
        myListView.setAdapter(myArrayAdapter);
    }

    public void getChildList() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getKey();
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getKey();
                myArrayList.remove(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(this.child).addChildEventListener(childEventListener);
    }

    public void myWrapper(String children) {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getKey();
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();

//                if (snapshot.getValue().toString()!=null){
//                    Comment comment = snapshot.getValue(Comment.class);
//                    myArrayList.add(comment.toString());
//                    myArrayAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
        if (children != null) {
//            myArrayList.removeAll(myArrayList);
            databaseReference.child(this.child).child(children).addChildEventListener(childEventListener);
        }
    }
}
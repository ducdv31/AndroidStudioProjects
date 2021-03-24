package com.example.colordetectiot;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

// Class Parent
class Database {
    static DatabaseReference VDatabase = FirebaseDatabase.getInstance().getReference();
}

// Set data
class setDatabase extends Database {
    private double Data;
    private String Text;
    private String childID;

    setDatabase() {

    }

    setDatabase(String childID, double Data) {
        this.childID = childID;
        this.Data = Data;
    }

    setDatabase(String childID, String Text) {
        this.childID = childID;
        this.Text = Text;
    }
    public void set() {
        VDatabase.child(childID).setValue(this.Data);

    }

    public void setText() {
        VDatabase.child(childID).setValue(this.Text);
    }
    public void setText(String data) {
        VDatabase.child(childID).setValue(data);
    }
}

// Get data
class getDatabase extends Database {
    private TextView id;
    private String child, unit;
    private String Second, Minute, Hour, Timer;

    getDatabase(TextView id, String child, String unit) {
        this.id = id;
        this.child = child;
        this.unit = unit;
    }

    void getData() {
        VDatabase.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id.setText(snapshot.getValue().toString() + unit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                id.setText("No Data");
            }
        });
    }

    void getDataDec1() {
        final DecimalFormat format = new DecimalFormat("#.0");
        VDatabase.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String t = snapshot.getValue().toString();
                float temp = Float.parseFloat(t);
                if (temp > -0.5 && temp < 0.5) {
                    id.setText("0" + unit);
                } else if (temp < 1 && temp >= 0.5 || temp > -1 && temp <= 0.5) {
                    String tempera = format.format(temp);
                    id.setText("0" + tempera + unit);
                } else {
                    String tempera = format.format(temp);
                    id.setText(tempera + unit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                id.setText("No Data");
            }
        });
    }

    void getString() {
        VDatabase.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                id.setText("No Data");
            }
        });
    }

    void getTimer() {
        VDatabase.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hour = snapshot.child("Hour").getValue().toString();
                Minute = snapshot.child("Minute").getValue().toString();
                Second = snapshot.child("Second").getValue().toString();
                Timer = Hour + " : " + Minute + " : " + Second;
                id.setText(Timer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

class getChildDatabase extends Database {
    private String child;
    private ListView myListView;
    private ArrayList<String> myArrayList; // = new ArrayList<>();
    private ArrayAdapter<String> myArrayAdapter; // new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrayList);

    getChildDatabase(ListView myListView, ArrayList<String> myArrayList, ArrayAdapter<String> myArrayAdapter, String child) {
        this.child = child;
        this.myListView = myListView;
        this.myArrayList = myArrayList;
        this.myArrayAdapter = myArrayAdapter;
        myListView.setAdapter(myArrayAdapter);
    }

    void getChildList() {
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
        VDatabase.child(this.child).addChildEventListener(childEventListener);
    }

    void myWrapper(String children) {
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
        VDatabase.addChildEventListener(childEventListener);
        if (children != null) {
//            myArrayList.removeAll(myArrayList);
            VDatabase.child(this.child).child(children).addChildEventListener(childEventListener);
        }
    }
}


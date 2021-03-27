package com.example.myhouse.DataDatabase;

import java.util.ArrayList;

public class SetDatabase extends Database {
    private double Data;
    private String Text;
    private String childID;

    SetDatabase() {

    }

    SetDatabase(String childID, double Data) {
        this.childID = childID;
        this.Data = Data;
    }

    SetDatabase(String childID, String Text) {
        this.childID = childID;
        this.Text = Text;
    }

    public SetDatabase(String childID) {
        this.childID = childID;
    }

    public void setStringData(String data) {
        databaseReference.child(childID).setValue(data);
    }

    public void setLongData(Long data) {
        databaseReference.child(childID).setValue(data);
    }

    public void setIntData(int data) {
        databaseReference.child(childID).setValue(data);
    }

    public void setStringArray(ArrayList<String> array) {
        for (int i = 0; i < array.size(); i++)
            databaseReference.child(childID + i).setValue(array.get(i));
    }

    public void set() {
        databaseReference.child(childID).setValue(this.Data);

    }

    public void setText() {
        databaseReference.child(childID).setValue(this.Text);
    }
}

package com.example.idmanager.model;

import java.io.Serializable;

public class User implements Serializable {
    private String ID;
    private String name;
    private int rank;
    private String room;

    public User(String ID, String name, int rank, String room) {
        this.ID = ID;
        this.name = name;
        this.rank = rank;
        this.room = room;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

package com.example.idmanager.model;

public class UserNID {
    private String Name;
    private String Room;
    private int Rank;

    public UserNID() {
    }

    public UserNID(String name, int rank, String room) {
        Name = name;
        Room = room;
        Rank = rank;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }
}

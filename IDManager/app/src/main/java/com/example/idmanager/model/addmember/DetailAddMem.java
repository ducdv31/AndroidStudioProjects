package com.example.idmanager.model.addmember;

public class DetailAddMem {
    private String name;
    private String room;
    private int rank;

    public DetailAddMem(String name, String room, int rank) {
        this.name = name;
        this.room = room;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

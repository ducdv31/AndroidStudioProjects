package com.example.recyclerviewgridcard;

class User {
    private String Name;
    private int birthday;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public User(String name, int birthday) {
        this.Name = name;
        this.birthday = birthday;
    }
}

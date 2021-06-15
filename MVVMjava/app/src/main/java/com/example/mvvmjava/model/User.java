package com.example.mvvmjava.model;

import androidx.databinding.Bindable;

public class User {
    private String name;
    private int num;

    public User(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return String.valueOf(num);
    }

    public void setNum(int num) {
        this.num = num;
    }
}

package com.example.dynamicaddfragmentviewpager;

import java.io.Serializable;

class Questions implements Serializable {
    private String name;

    public Questions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

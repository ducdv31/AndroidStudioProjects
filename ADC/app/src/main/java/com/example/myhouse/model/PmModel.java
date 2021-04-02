package com.example.myhouse.model;

public class PmModel {
    private int pm1;
    private int pm25;
    private int pm10;

    public PmModel() {
    }

    public PmModel(int pm1, int pm25, int pm10) {
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    public int getPm1() {
        return pm1;
    }

    public void setPm1(int pm1) {
        this.pm1 = pm1;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }
}

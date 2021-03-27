package com.example.myhouse.chart;

public class DataPoint {
    int x, y;
    public DataPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DataPoint(){
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}

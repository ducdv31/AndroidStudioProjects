package com.example.myhouse.datalocation;

import java.util.HashMap;
import java.util.Map;

public class DataLocation {
    private double Latitude;
    private double Longitude;
    private Map<String, Double> location = new HashMap<>();

    public DataLocation() {
        // getValue
    }

    public DataLocation(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public Map<String, Double> toMap() { // To set Data to Database
        HashMap<String, Double> map = new HashMap<>();
        map.putIfAbsent("Latitude", Latitude);
        map.putIfAbsent("Longitude", Longitude);
        return map;
    }

}

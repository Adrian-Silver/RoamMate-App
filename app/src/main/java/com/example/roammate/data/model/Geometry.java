package com.example.roammate.data.model;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    @SerializedName("type")
    private String type;

    @SerializedName("coordinates")
    private double[] coordinates;

    public String getType() {
        return type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    // Helper methods to get longitude and latitude
    public double getLongitude() {
        return coordinates != null && coordinates.length > 0 ? coordinates[0] : 0;
    }

    public double getLatitude() {
        return coordinates != null && coordinates.length > 1 ? coordinates[1] : 0;
    }
}


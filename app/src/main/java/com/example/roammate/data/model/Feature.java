package com.example.roammate.data.model;

import com.google.gson.annotations.SerializedName;

public class Feature {
    @SerializedName("type")
    private String type;

    @SerializedName("properties")
    private Properties properties;

    @SerializedName("geometry")
    private Geometry geometry;

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}


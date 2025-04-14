package com.example.roammate.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoapifyResponse {
    @SerializedName("type")
    private String type;

    @SerializedName("features")
    private List<Feature> features;

    public String getType() {
        return type;
    }

    public List<Feature> getFeatures() {
        return features;
    }
}


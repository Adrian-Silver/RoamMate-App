package com.example.roammate.data.model;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    @SerializedName("type")
    private String type;

    @SerializedName("coordinates")
//    private double[] coordinates;
    private Object coordinates;

    public String getType() {
        return type;
    }

//    public double[] getCoordinates() {
//        return coordinates;
//    }
    public Object getCoordinates() {
        return coordinates;
    }

//    // Helper methods to get longitude and latitude
//    public double getLongitude() {
//        return coordinates != null && coordinates.length > 0 ? coordinates[0] : 0;
//    }
//
//    public double getLatitude() {
//        return coordinates != null && coordinates.length > 1 ? coordinates[1] : 0;
//    }

    // Helper methods to get longitude and latitude
    public double getLongitude() {
        if (coordinates == null) {
            return 0;
        }

        try {
            if ("Point".equals(type) && coordinates instanceof double[]) {
                // For Point geometry
                double[] coords = (double[]) coordinates;
                return coords.length > 0 ? coords[0] : 0;
            } else if (coordinates instanceof Object[]) {
                // For LineString, Polygon, etc.
                Object[] polygonCoords = (Object[]) coordinates;
                if (polygonCoords.length > 0) {
                    // Get first set of coordinates (first polygon or line)
                    Object firstCoord = polygonCoords[0];
                    if (firstCoord instanceof Object[]) {
                        Object[] firstSet = (Object[]) firstCoord;
                        if (firstSet.length > 0) {
                            // Get first point
                            Object point = firstSet[0];
                            if (point instanceof double[]) {
                                double[] coords = (double[]) point;
                                return coords.length > 0 ? coords[0] : 0;
                            }
                        }
                    } else if (firstCoord instanceof double[]) {
                        double[] coords = (double[]) firstCoord;
                        return coords.length > 0 ? coords[0] : 0;
                    }
                }
            }
        } catch (Exception e) {
            // If any error in parsing, return 0
            return 0;
        }
        return 0;
    }

    public double getLatitude() {
        if (coordinates == null) {
            return 0;
        }

        try {
            if ("Point".equals(type) && coordinates instanceof double[]) {
                // For Point geometry
                double[] coords = (double[]) coordinates;
                return coords.length > 1 ? coords[1] : 0;
            } else if (coordinates instanceof Object[]) {
                // For LineString, Polygon, etc.
                Object[] polygonCoords = (Object[]) coordinates;
                if (polygonCoords.length > 0) {
                    // Get first set of coordinates (first polygon or line)
                    Object firstCoord = polygonCoords[0];
                    if (firstCoord instanceof Object[]) {
                        Object[] firstSet = (Object[]) firstCoord;
                        if (firstSet.length > 0) {
                            // Get first point
                            Object point = firstSet[0];
                            if (point instanceof double[]) {
                                double[] coords = (double[]) point;
                                return coords.length > 1 ? coords[1] : 0;
                            }
                        }
                    } else if (firstCoord instanceof double[]) {
                        double[] coords = (double[]) firstCoord;
                        return coords.length > 1 ? coords[1] : 0;
                    }
                }
            }
        } catch (Exception e) {
            // If any error in parsing, return 0
            return 0;
        }
        return 0;
    }
}


package com.example.roammate.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Properties {
    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("state")
    private String state;

    @SerializedName("county")
    private String county;

    @SerializedName("city")
    private String city;

    @SerializedName("postcode")
    private String postcode;

    @SerializedName("suburb")
    private String suburb;

    @SerializedName("street")
    private String street;

    @SerializedName("housenumber")
    private String houseNumber;

    @SerializedName("lon")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("formatted")
    private String formatted;

    @SerializedName("address_line1")
    private String addressLine1;

    @SerializedName("address_line2")
    private String addressLine2;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("details")
    private List<String> details;

    @SerializedName("distance")
    private double distance;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("website")
    private String website;

    @SerializedName("phone")
    private String phone;

    @SerializedName("opening_hours")
    private String openingHours;

    @SerializedName("datasource")
    private Datasource datasource;

    @SerializedName("contact")
    private Map<String, String> contact;

    @SerializedName("catering")
    private Map<String, Object> catering;

    // Nested Datasource class
    public static class Datasource {
        @SerializedName("sourcename")
        private String sourceName;

        @SerializedName("attribution")
        private String attribution;

        @SerializedName("license")
        private String license;

        @SerializedName("url")
        private String url;

        // Getters
        public String getSourceName() {
            return sourceName;
        }

        public String getAttribution() {
            return attribution;
        }

        public String getLicense() {
            return license;
        }

        public String getUrl() {
            return url;
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getDetails() {
        return details;
    }

    public double getDistance() {
        return distance;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public Map<String, String> getContact() {
        return contact;
    }

    public Map<String, Object> getCatering() {
        return catering;
    }
}

package com.example.roammate.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_places")
public class SavedPlaceEntity {

    @PrimaryKey
    @NonNull
    private String placeId;

    private String name;
    private String category;
    private String address;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private float rating;
    private String city;
    private String country;
    private String website;
    private String phone;
    private String formattedAddress;

    // Constructor
    public SavedPlaceEntity(
            @NonNull
            String placeId,
            String name,
            String category,
            String address,
            double latitude,
            double longitude,
            String imageUrl,
            float rating,
            String city,
            String country,
            String website,
            String phone,
            String formattedAddress
    ) {
        this.placeId = placeId;
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.city = city;
        this.country = country;
        this.website = website;
        this.phone = phone;
        this.formattedAddress = formattedAddress;
    }

    // Getters
    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    // Setters
    public void setPlaceId(@NonNull String placeId) {
        this.placeId = placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}


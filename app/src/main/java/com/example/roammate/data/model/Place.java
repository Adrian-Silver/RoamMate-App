package com.example.roammate.data.model;

import java.util.List;

public class Place {
    private String id;
    private String name;
    private String category;
    private String address;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private float rating;
    private boolean isSaved;
    private String formattedAddress;
    private List<String> categories;
    private String placeId;
    private String website;
    private String phone;
    private String openingHours;
    private double distance;
    private String city;
    private String country;

    // Constructor for creating a Place object from a Geoapify Feature
    public Place(Feature feature) {
        Properties props = feature.getProperties();
        Geometry geo = feature.getGeometry();

        this.placeId = props.getPlaceId();
        this.name = props.getName() != null ? props.getName() : "Unnamed Place";
        this.address = props.getFormatted();
        this.formattedAddress = props.getFormatted();
        this.latitude = props.getLatitude();
        this.longitude = props.getLongitude();
        this.categories = props.getCategories();
        this.website = props.getWebsite();
        this.phone = props.getPhone();
        this.openingHours = props.getOpeningHours();
        this.distance = props.getDistance();
        this.city = props.getCity();
        this.country = props.getCountry();
        this.isSaved = false;

        // Determine primary category from categories list
        if (categories != null && !categories.isEmpty()) {
            // Find the most specific category (usually the last one)
            this.category = categories.get(categories.size() - 1);
        } else {
            this.category = "uncategorized";
        }

        // Set a placeholder rating (Geoapify doesn't provide ratings)
        this.rating = (float) (3.0 + Math.random() * 2.0); // Random rating between 3 and 5

        // We would get an image URL from another API or use a placeholder
        this.imageUrl = "";
    }

    // Constructor for creating a Place from a database entity
    public Place(String id, String name, String category, String address,
                 double latitude, double longitude, String imageUrl,
                 float rating, boolean isSaved, String placeId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.isSaved = isSaved;
        this.placeId = placeId;
    }

    // Getters and setters
    public String getId() {
        return id != null ? id : placeId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public List<String> getCategories() {
        return categories;
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

    public double getDistance() {
        return distance;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // Helper methods

    public String getCategoryDisplayName() {
        if (category == null) return "Place";

        // Convert "catering.restaurant.italian" to "Italian Restaurant"
        String[] parts = category.split("\\.");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1];
            // Capitalize first letter
            if (lastPart.length() > 0) {
                lastPart = lastPart.substring(0, 1).toUpperCase() + lastPart.substring(1);
            }

            // Add the general category if it exists
            if (parts.length > 1) {
                String generalCategory = parts[parts.length - 2];
                if (generalCategory.length() > 0) {
                    generalCategory = generalCategory.substring(0, 1).toUpperCase() + generalCategory.substring(1);
                }
                return lastPart + " " + generalCategory;
            }
            return lastPart;
        }
        return category;
    }

    public String getDistanceText() {
        if (distance < 0.1) {
            return "Nearby";
        } else if (distance < 1) {
            return String.format("%.0f m", distance * 1000);
        } else {
            return String.format("%.1f km", distance);
        }
    }

    public String getSimpleAddress() {
        if (address == null) return "";

        // Try to extract just the street address
        if (address.contains(",")) {
            return address.substring(0, address.indexOf(","));
        }
        return address;
    }
}


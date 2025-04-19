package com.example.roammate.data.model;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String fee;
    private String leisure;
    private String historic;
    private Integer stars;
    private String accommodationType;
    private String internetAccess;
    private Map<String, Boolean> paymentOptions;
    private String cuisine;
    private Map<String, Object> facilities;
    private Map<String, Object> raw;
    private String state;
    private String county;

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
        this.raw = props.getRaw();

//        // Determine primary category from categories list
//        if (categories != null && !categories.isEmpty()) {
//            // Find the most specific category (usually the last one)
//            this.category = categories.get(categories.size() - 1);
//        } else {
//            this.category = "uncategorized";
//        }
        // Determine primary category from categories list
        if (categories != null && !categories.isEmpty()) {
            // Find the most appropriate category for saving/filtering
            if (containsCategory(categories, "tourism.attraction") ||
                    containsCategory(categories, "tourism.sights")) {
                this.category = "tourism.attraction";
            } else if (containsCategory(categories, "accommodation.hotel") ||
                    containsCategory(categories, "accommodation")) {
                this.category = "accommodation.hotel";
            } else if (containsCategory(categories, "catering.restaurant") ||
                    containsCategory(categories, "catering")) {
                this.category = "catering.restaurant";
            } else if (containsCategory(categories, "tourism.information")) {
                this.category = "tourism.information";
            } else if (!categories.isEmpty()) {
                // Use the most specific category (usually the last one)
                this.category = categories.get(categories.size() - 1);
            } else {
                this.category = "uncategorized";
            }
        } else {
            this.category = "uncategorized";
        }

        // Log the category assignment
        Log.d("Place", "Assigned category: " + this.category + " from categories: " + categories);

//        // Parse category-specific fields
//        parseAdditionalFields();

        // Set a placeholder rating (Geoapify doesn't provide ratings)
        this.rating = (float) (3.0 + Math.random() * 2.0); // Random rating between 3 and 5

        // We would get an image URL from another API or use a placeholder
        this.imageUrl = "";

        // Parse category-specific fields
        if (raw != null) {
            // For attractions
            if (raw.containsKey("fee")) {
                this.fee = raw.get("fee").toString();
            }
            if (raw.containsKey("leisure")) {
                this.leisure = raw.get("leisure").toString();
            }
            if (raw.containsKey("historic")) {
                this.historic = raw.get("historic").toString();
            }

            // For hotels
            if (raw.containsKey("stars")) {
                try {
                    this.stars = Integer.parseInt(raw.get("stars").toString());
                } catch (NumberFormatException e) {
                    this.stars = null;
                }
            }
            if (raw.containsKey("tourism") && "hotel".equals(raw.get("tourism"))) {
                this.accommodationType = "Hotel";
            } else if (raw.containsKey("tourism")) {
                this.accommodationType = raw.get("tourism").toString();
            }
            if (raw.containsKey("internet_access")) {
                this.internetAccess = raw.get("internet_access").toString();
            }

            // For restaurants
            if (raw.containsKey("cuisine")) {
                this.cuisine = raw.get("cuisine").toString();
            }
        }

        // Parse facilities from contact, catering, etc.
        this.facilities = new HashMap<>();
        if (props.getContact() != null) {
            this.facilities.putAll(props.getContact());
        }
        if (props.getCatering() != null) {
            this.facilities.putAll(props.getCatering());
        }

        // Parse payment options
        this.paymentOptions = new HashMap<>();
        if (raw != null) {
            for (Map.Entry<String, Object> entry : raw.entrySet()) {
                if (entry.getKey().startsWith("payment:")) {
                    try {
                        this.paymentOptions.put(
                                entry.getKey().substring("payment:".length()),
                                "yes".equals(entry.getValue().toString())
                        );
                    } catch (Exception e) {
                        // Skip if any parsing errors
                    }
                }
            }
        }
    }

//    // Constructor that can be used for geocoding results
//    public Place(String id, String name, String category, String address,
//                 double latitude, double longitude, String imageUrl,
//                 float rating, boolean isSaved, String placeId) {
//        this.id = id;
//        this.name = name;
//        this.category = category;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.imageUrl = imageUrl;
//        this.rating = rating;
//        this.isSaved = isSaved;
//        this.placeId = placeId;
//    }


    // Constructor for creating a Place from a database entity, also use for Geocoding results
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

    public String getFee() {
        return fee;
    }

    public String getLeisure() {
        return leisure;
    }

    public String getHistoric() {
        return historic;
    }

    public Integer getStars() {
        return stars;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public String getInternetAccess() {
        return internetAccess;
    }

    public Map<String, Boolean> getPaymentOptions() {
        return paymentOptions;
    }

    public String getCuisine() {
        return cuisine;
    }

    public Map<String, Object> getFacilities() {
        return facilities;
    }

    public Map<String, Object> getRaw() {
        return raw;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }



    // Helper methods

    /**
     * Helper method to check if categories list contains a specific category
     */
    private boolean containsCategory(List<String> categories, String categoryToFind) {
        if (categories == null) return false;

        for (String category : categories) {
            if (category.equals(categoryToFind) || category.startsWith(categoryToFind)) {
                return true;
            }
        }
        return false;
    }



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

    public String getState() {
        return state;
    }
}


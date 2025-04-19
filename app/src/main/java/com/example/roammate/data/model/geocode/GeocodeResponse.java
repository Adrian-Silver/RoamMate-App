package com.example.roammate.data.model.geocode;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GeocodeResponse {
    @SerializedName("results")
    private List<GeocodingResult> results;

    @SerializedName("query")
    private Map<String, Object> query;

    public List<GeocodingResult> getResults() {
        return results;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public static class GeocodingResult {
        @SerializedName("datasource")
        private Map<String, String> datasource;

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

        @SerializedName("lon")
        private double longitude;

        @SerializedName("lat")
        private double latitude;

        @SerializedName("result_type")
        private String resultType;

        @SerializedName("formatted")
        private String formatted;

        @SerializedName("address_line1")
        private String addressLine1;

        @SerializedName("address_line2")
        private String addressLine2;

        @SerializedName("category")
        private String category;

        @SerializedName("timezone")
        private Map<String, Object> timezone;

        @SerializedName("plus_code")
        private String plusCode;

        @SerializedName("rank")
        private Map<String, Object> rank;

        @SerializedName("place_id")
        private String placeId;

        @SerializedName("bbox")
        private Map<String, Double> bbox;

        public Map<String, String> getDatasource() {
            return datasource;
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

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getResultType() {
            return resultType;
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

        public String getCategory() {
            return category;
        }

        public Map<String, Object> getTimezone() {
            return timezone;
        }

        public String getPlusCode() {
            return plusCode;
        }

        public Map<String, Object> getRank() {
            return rank;
        }

        public String getPlaceId() {
            return placeId;
        }

        public Map<String, Double> getBbox() {
            return bbox;
        }
    }
}

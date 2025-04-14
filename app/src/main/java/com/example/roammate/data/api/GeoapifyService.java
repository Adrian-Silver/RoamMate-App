package com.example.roammate.data.api;

import com.example.roammate.data.model.GeoapifyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for Geoapify API
 */
public interface GeoapifyService {

    /**
     * Search for places based on category and location filter
     */
    @GET("v2/places")
    Call<GeoapifyResponse> searchPlaces(
            @Query("categories") String categories,
            @Query("filter") String filter,
            @Query("limit") int limit,
            @Query("apiKey") String apiKey
    );

    /**
     * Get place suggestions based on text input
     */
    @GET("v1/geocode/autocomplete")
    Call<GeoapifyResponse> getPlaceSuggestions(
            @Query("text") String text,
            @Query("limit") int limit,
            @Query("apiKey") String apiKey
    );

    /**
     * Search for places near a specific location
     */
    @GET("v2/places")
    Call<GeoapifyResponse> searchNearby(
            @Query("categories") String categories,
            @Query("bias") String bias,
            @Query("limit") int limit,
            @Query("apiKey") String apiKey
    );

    /**
     * Get details for a specific place
     */
    @GET("v2/place-details")
    Call<GeoapifyResponse> getPlaceDetails(
            @Query("id") String placeId,
            @Query("apiKey") String apiKey
    );
}

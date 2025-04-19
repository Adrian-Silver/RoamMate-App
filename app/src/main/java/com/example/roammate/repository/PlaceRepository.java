package com.example.roammate.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roammate.data.api.ApiClient;
import com.example.roammate.data.api.GeoapifyService;
import com.example.roammate.data.AppDatabase;
import com.example.roammate.data.PlaceDao;
import com.example.roammate.data.SavedPlaceEntity;
import com.example.roammate.data.model.GeoapifyResponse;
import com.example.roammate.data.model.Place;
import com.example.roammate.data.model.Feature;
import com.example.roammate.data.model.geocode.GeocodeResponse;
import com.example.roammate.util.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class - works with local and remote data sources
 */
public class PlaceRepository {
    private static final String TAG = "PlaceRepository";

    private final PlaceDao placeDao;
    private final GeoapifyService apiService;
    private final String apiKey;

    /**
     * Constructor
     */
    public PlaceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        placeDao = db.placeDao();
        apiService = ApiClient.getClient().create(GeoapifyService.class);
        apiKey = ApiClient.getApiKey();
    }

    /**
     * Get places by location and category
     */
    public LiveData<Resource<List<Place>>> getPlacesByLocation(String placeId, String categories, int limit) {
        MutableLiveData<Resource<List<Place>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Create the filter parameter for the API call
        String filter = "place:" + placeId;

        // Make the API call
        apiService.searchPlaces(categories, filter, limit, apiKey)
                .enqueue(new Callback<GeoapifyResponse>() {
                    @Override
                    public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Place> places = convertFeaturesToPlaces(response.body().getFeatures());
                            result.postValue(Resource.success(places));
                        } else {
                            result.postValue(Resource.error(
                                    "Error code: " + response.code(), null));
                        }
                    }

                    @Override
                    public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                        result.postValue(Resource.error(t.getMessage(), null));
                        Log.e(TAG, "API call failed", t);
                    }
                });

        return result;
    }

    /**
     * Search for places based on coordinates (nearby search)
     */
    public LiveData<Resource<List<Place>>> searchNearby(double lat, double lon, String categories, int limit) {
        MutableLiveData<Resource<List<Place>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Create the proximity bias parameter
        String bias = "proximity:" + lon + "," + lat;

        // Make the API call
        apiService.searchNearby(categories, bias, limit, apiKey)
                .enqueue(new Callback<GeoapifyResponse>() {
                    @Override
                    public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Place> places = convertFeaturesToPlaces(response.body().getFeatures());
                            result.postValue(Resource.success(places));
                        } else {
                            result.postValue(Resource.error(
                                    "Error code: " + response.code(), null));
                        }
                    }

                    @Override
                    public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                        result.postValue(Resource.error(t.getMessage(), null));
                        Log.e(TAG, "API call failed", t);
                    }
                });

        return result;
    }

    /**
     * Get place suggestions based on text input
     */
    public LiveData<Resource<List<Place>>> getPlaceSuggestions(String query) {
        MutableLiveData<Resource<List<Place>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Make the API call
        apiService.getPlaceSuggestions(query, 5, apiKey)
                .enqueue(new Callback<GeoapifyResponse>() {
                    @Override
                    public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Place> places = convertFeaturesToPlaces(response.body().getFeatures());
                            result.postValue(Resource.success(places));
                        } else {
                            result.postValue(Resource.error(
                                    "Error code: " + response.code(), null));
                        }
                    }

                    @Override
                    public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                        result.postValue(Resource.error(t.getMessage(), null));
                        Log.e(TAG, "API call failed", t);
                    }
                });

        return result;
    }

    /**
     * Get details for a specific place
     */
    public LiveData<Resource<Place>> getPlaceDetails(String placeId) {
        MutableLiveData<Resource<Place>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // First check if the place is in the database
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int count = placeDao.isPlaceSaved(placeId);
            if (count > 0) {
                // Place is saved, get it from the database
                LiveData<SavedPlaceEntity> dbPlace = placeDao.getPlaceById(placeId);
                // Observe the LiveData from a background thread
                // This is a workaround as we can't directly get the value from LiveData
                // In a real app, you might want to use a different approach
                try {
                    Thread.sleep(100); // Wait for LiveData to be ready
                    // This is just for demonstration, not ideal for production
                    if (dbPlace.getValue() != null) {
                        result.postValue(Resource.success(convertEntityToPlace(dbPlace.getValue())));
                        return;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // If not in database or couldn't get it, fetch from API
            apiService.getPlaceDetails(placeId, apiKey)
                    .enqueue(new Callback<GeoapifyResponse>() {
                        @Override
                        public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
//                            if (response.isSuccessful() && response.body() != null &&
//                                    !response.body().getFeatures().isEmpty()) {
//                                Place place = new Place(response.body().getFeatures().get(0));
//                                result.postValue(Resource.success(place));
//                            } else {
//                                result.postValue(Resource.error(
//                                        "Error code: " + response.code(), null));
//                            }
                            try {
                                if (response.isSuccessful() && response.body() != null &&
                                        response.body().getFeatures() != null &&
                                        !response.body().getFeatures().isEmpty()) {
                                    Place place = new Place(response.body().getFeatures().get(0));
                                    result.postValue(Resource.success(place));
                                } else {
                                    String errorMsg = "Error code: " + response.code();
                                    try {
                                        if (response.errorBody() != null) {
                                            errorMsg += " - " + response.errorBody().string();
                                        }
                                    } catch (IOException e) {
                                        Log.e(TAG, "Error reading error body", e);
                                    }
                                    result.postValue(Resource.error(errorMsg, null));
                                }
                            } catch (Exception e) {
                                // Catch any parsing exceptions
                                Log.e(TAG, "Error parsing place details", e);
                                result.postValue(Resource.error("Error parsing place details: " + e.getMessage(), null));
                            }
                        }

                        @Override
                        public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                            result.postValue(Resource.error(t.getMessage(), null));
                            Log.e(TAG, "API call failed", t);
                        }
                    });
        });

        return result;
    }

    /**
     * Save a place to the database
     */
    public void savePlace(Place place) {
        SavedPlaceEntity entity = convertPlaceToEntity(place);

        // Log the save operation for debugging
        Log.d(TAG, "Saving place: " + place.getName() + ", category: " + place.getCategory() + ", ID: " + place.getPlaceId());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            placeDao.insertPlace(entity);
        });
    }

    /**
     * Remove a place from saved places
     */
    public void removePlace(String placeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            placeDao.deletePlaceById(placeId);
        });
    }

    /**
     * Check if a place is saved
     */
    public LiveData<Boolean> isPlaceSaved(String placeId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            int count = placeDao.isPlaceSaved(placeId);
            result.postValue(count > 0);
        });

        return result;
    }

    /**
     * Get all saved places
     */
    public LiveData<List<SavedPlaceEntity>> getAllSavedPlaces() {
        return placeDao.getAllSavedPlaces();
    }

    /**
     * Get saved places by category
     */
    public LiveData<List<SavedPlaceEntity>> getSavedPlacesByCategory(String category) {
        return placeDao.getPlacesByCategory(category);
    }

    // Helper methods

    /**
     * Convert a list of Feature objects to a list of Place objects
     */
    private List<Place> convertFeaturesToPlaces(List<Feature> features) {
        List<Place> places = new ArrayList<>();
        for (Feature feature : features) {
            places.add(new Place(feature));
        }
        return places;
    }

    /**
     * Convert a Place object to a SavedPlaceEntity
     */
    private SavedPlaceEntity convertPlaceToEntity(Place place) {
        return new SavedPlaceEntity(
                place.getPlaceId(),
                place.getName(),
                place.getCategory(),
                place.getAddress(),
                place.getLatitude(),
                place.getLongitude(),
                place.getImageUrl(),
                place.getRating(),
                place.getCity(),
                place.getCountry(),
                place.getWebsite(),
                place.getPhone(),
                place.getFormattedAddress()
        );
    }

    /**
     * Convert a SavedPlaceEntity to a Place object
     */
    private Place convertEntityToPlace(SavedPlaceEntity entity) {
        Place place = new Place(
                entity.getPlaceId(),
                entity.getName(),
                entity.getCategory(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getImageUrl(),
                entity.getRating(),
                true, // It's saved
                entity.getPlaceId()
        );
        return place;
    }

    /**
     * Search for places by name (cities, regions, etc.)
     * @param name The name to search for
     * @param limit Maximum number of results
     * @return LiveData containing search results
     */
    public LiveData<Resource<List<Place>>> searchPlacesByName(String name, int limit) {
        MutableLiveData<Resource<List<Place>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Use the format=json parameter as shown in the example
        apiService.searchPlacesByName(name, "json", limit, apiKey)
                .enqueue(new Callback<GeocodeResponse>() {
                    @Override
                    public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                            List<Place> places = convertGeocodingResultsToPlaces(response.body().getResults());
                            result.postValue(Resource.success(places));
                        } else {
                            String errorMsg = "Error code: " + response.code();
                            try {
                                if (response.errorBody() != null) {
                                    errorMsg += " - " + response.errorBody().string();
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Error reading error body", e);
                            }
                            result.postValue(Resource.error(errorMsg, null));
                        }
                    }

                    @Override
                    public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                        result.postValue(Resource.error(t.getMessage(), null));
                        Log.e(TAG, "API call failed", t);
                    }
                });

        return result;
    }

    /**
     * Convert geocoding results to Place objects
     */
    private List<Place> convertGeocodingResultsToPlaces(List<GeocodeResponse.GeocodingResult> results) {
        List<Place> places = new ArrayList<>();

        for (GeocodeResponse.GeocodingResult result : results) {
            // Create a Place object
            Place place = new Place(
                    result.getPlaceId(),
                    result.getCity() != null ? result.getCity() : result.getAddressLine1(),
                    result.getCategory(),
                    result.getFormatted(),
                    result.getLatitude(),
                    result.getLongitude(),
                    null, // No image URL from geocoding
                    4.0f,  // Default rating since not provided
                    false, // Not saved by default
                    result.getPlaceId()
            );

            // Set additional fields
            place.setCity(result.getCity());
            place.setCountry(result.getCountry());
            place.setState(result.getState());
            place.setCounty(result.getCounty());
            place.setFormattedAddress(result.getFormatted());

            places.add(place);
        }

        return places;
    }


    /**
     * Search for POIs within a specific place
     * @param placeId The ID of the place to search within
     * @param categories Categories to filter by (comma-separated)
     * @param limit Maximum number of results
     * @return LiveData containing search results
     */
    public LiveData<Resource<List<Place>>> searchPOIsInPlace(String placeId, String categories, int limit) {
        MutableLiveData<Resource<List<Place>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Create the filter parameter for the API call
        String filter = "place:" + placeId;

        apiService.searchPOIsInPlace(categories, filter, limit, apiKey)
                .enqueue(new Callback<GeoapifyResponse>() {
                    @Override
                    public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getFeatures() != null && !response.body().getFeatures().isEmpty()) {
                                    List<Place> places = convertFeaturesToPlaces(response.body().getFeatures());
                                    result.postValue(Resource.success(places));
                                } else {
                                    // Successfully got a response but no places found
                                    result.postValue(Resource.success(new ArrayList<>()));
                                }
                            } else {
                                String errorMsg = "Error code: " + response.code();
                                try {
                                    if (response.errorBody() != null) {
                                        errorMsg += " - " + response.errorBody().string();
                                    }
                                } catch (IOException e) {
                                    Log.e(TAG, "Error reading error body", e);
                                }
                                result.postValue(Resource.error(errorMsg, null));
                            }
                        } catch (Exception e) {
                            // Handle any parsing exceptions
                            Log.e(TAG, "Error parsing POI results", e);
                            result.postValue(Resource.error("Error parsing results: " + e.getMessage(), null));
                        }
                    }



                    @Override
                    public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                        result.postValue(Resource.error(t.getMessage(), null));
                        Log.e(TAG, "API call failed", t);
                    }
                });

        return result;
    }
}

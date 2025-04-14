package com.example.roammate.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roammate.data.model.Place;
import com.example.roammate.repository.PlaceRepository;
import com.example.roammate.util.Resource;

import java.util.List;

/**
 * ViewModel for fetching and managing place data
 */
public class PlaceViewModel extends AndroidViewModel {

    private final PlaceRepository repository;

    /**
     * Constructor
     */
    public PlaceViewModel(Application application) {
        super(application);
        repository = new PlaceRepository(application);
    }

    /**
     * Get places by location and category
     */
    public LiveData<Resource<List<Place>>> getPlacesByLocation(String placeId, String category, int limit) {
        return repository.getPlacesByLocation(placeId, category, limit);
    }

    /**
     * Search for places near a location
     */
    public LiveData<Resource<List<Place>>> searchNearby(double lat, double lon, String category, int limit) {
        return repository.searchNearby(lat, lon, category, limit);
    }

    /**
     * Get place suggestions based on text input
     */
    public LiveData<Resource<List<Place>>> getPlaceSuggestions(String query) {
        return repository.getPlaceSuggestions(query);
    }

    /**
     * Get details for a specific place
     */
    public LiveData<Resource<Place>> getPlaceDetails(String placeId) {
        return repository.getPlaceDetails(placeId);
    }

    /**
     * Save a place
     */
    public void savePlace(Place place) {
        repository.savePlace(place);
    }

    /**
     * Remove a place from saved places
     */
    public void removePlace(String placeId) {
        repository.removePlace(placeId);
    }

    /**
     * Check if a place is saved
     */
    public LiveData<Boolean> isPlaceSaved(String placeId) {
        return repository.isPlaceSaved(placeId);
    }
}


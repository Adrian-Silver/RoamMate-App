package com.example.roammate.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roammate.data.SavedPlaceEntity;
import com.example.roammate.repository.PlaceRepository;

import java.util.List;

public class SavedPlaceViewModel extends AndroidViewModel {

    private final PlaceRepository repository;

    /**
     * Constructor
     */
    public SavedPlaceViewModel(Application application) {
        super(application);
        repository = new PlaceRepository(application);
    }

    /**
     * Get all saved places
     */
    public LiveData<List<SavedPlaceEntity>> getAllSavedPlaces() {
        return repository.getAllSavedPlaces();
    }

    /**
     * Get saved places by category
     */
    public LiveData<List<SavedPlaceEntity>> getSavedPlaces(String category) {
        return repository.getSavedPlacesByCategory(category);
    }

    /**
     * Remove a place from saved places
     */
    public void removeSavedPlace(String placeId) {
        repository.removePlace(placeId);
    }
}


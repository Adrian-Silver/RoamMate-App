package com.example.roammate.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roammate.data.SavedPlaceEntity;
import com.example.roammate.repository.PlaceRepository;

import java.util.List;

public class SavedPlaceViewModel extends AndroidViewModel {

    private final PlaceRepository repository;
    private static final String TAG = "SavedPlaceViewModel";


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

//    /**
//     * Get saved places by category
//     */
//    public LiveData<List<SavedPlaceEntity>> getSavedPlaces(String category) {
//        return repository.getSavedPlacesByCategory(category);
//    }

    /**
     * Get saved places by category
     */
    public LiveData<List<SavedPlaceEntity>> getSavedPlaces(String category) {
        // Determine appropriate search pattern based on category
        String searchPattern;
        switch (category) {
            case "attraction":
                searchPattern = "tourism"; // Matches tourism.attraction, tourism.sights, etc.
                break;
            case "hotel":
                searchPattern = "accommodation"; // Matches accommodation.hotel, etc.
                break;
            case "restaurant":
                searchPattern = "catering"; // Matches catering.restaurant, etc.
                break;
            case "tip":
                searchPattern = "information"; // Matches tourism.information
                break;
            default:
                searchPattern = category;
                break;
        }

        Log.d(TAG, "Getting saved places for category pattern: " + searchPattern);
        return repository.getSavedPlacesByCategory(searchPattern);
    }

    /**
     * Remove a place from saved places
     */
    public void removeSavedPlace(String placeId) {
        repository.removePlace(placeId);
    }
}


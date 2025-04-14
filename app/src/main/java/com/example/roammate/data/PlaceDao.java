package com.example.roammate.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaceDao {

    /**
     * Insert a place into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlace(SavedPlaceEntity place);

    /**
     * Delete a place from the database
     */
    @Delete
    void deletePlace(SavedPlaceEntity place);

    /**
     * Delete a place from the database by place ID
     */
    @Query("DELETE FROM saved_places WHERE placeId = :placeId")
    void deletePlaceById(String placeId);

    /**
     * Get all saved places
     */
    @Query("SELECT * FROM saved_places")
    LiveData<List<SavedPlaceEntity>> getAllSavedPlaces();

    /**
     * Get saved places by category
     */
    @Query("SELECT * FROM saved_places WHERE category LIKE '%' || :category || '%'")
    LiveData<List<SavedPlaceEntity>> getPlacesByCategory(String category);

    /**
     * Get a specific saved place by ID
     */
    @Query("SELECT * FROM saved_places WHERE placeId = :placeId")
    LiveData<SavedPlaceEntity> getPlaceById(String placeId);

    /**
     * Check if a place is saved
     */
    @Query("SELECT COUNT(*) FROM saved_places WHERE placeId = :placeId")
    int isPlaceSaved(String placeId);
}

package com.example.roammate.util;

import com.example.roammate.R;

import java.util.Random;

public class CategoryImageProvider {
    // Arrays containing resource IDs for different categories
    private static final int[] HOTEL_IMAGES = {
            R.drawable.hotel_1,
            R.drawable.hotel_2,
            R.drawable.hotel_3,
            R.drawable.hotel_4,
            R.drawable.hotel_5
    };

    private static final int[] RESTAURANT_IMAGES = {
            R.drawable.restaurant1,
            R.drawable.restaurant2,
            R.drawable.restaurant3,
            R.drawable.restaurant4,
            R.drawable.restaurant5
    };

    private static final int[] ATTRACTION_IMAGES = {
            R.drawable.attraction1,
            R.drawable.attraction2,
            R.drawable.attraction_venice,
            R.drawable.attraction4,
            R.drawable.attraction5
    };

    // Default image used when category doesn't match
    private static final int DEFAULT_IMAGE = R.drawable.default_place;

    private static final Random random = new Random();

    /**
     * Get a random image resource ID based on the place category
     *
     * @param category The category of the place
     * @return A random image resource ID appropriate for the category
     */
    public static int getRandomImageForCategory(String category) {
        if (category == null) {
            return DEFAULT_IMAGE;
        }

        // Convert category to lowercase for case-insensitive comparison
        String lowerCategory = category.toLowerCase();

        if (lowerCategory.contains("hotel") || lowerCategory.contains("accommodation")) {
            return HOTEL_IMAGES[random.nextInt(HOTEL_IMAGES.length)];
        } else if (lowerCategory.contains("restaurant") || lowerCategory.contains("food") ||
                lowerCategory.contains("catering")) {
            return RESTAURANT_IMAGES[random.nextInt(RESTAURANT_IMAGES.length)];
        } else if (lowerCategory.contains("attraction") || lowerCategory.contains("tourism") ||
                lowerCategory.contains("sights")) {
            return ATTRACTION_IMAGES[random.nextInt(ATTRACTION_IMAGES.length)];
        }

        // If no matching category is found, use a default image
        return DEFAULT_IMAGE;
    }

    /**
     * Get a specific image based on place ID to ensure the same image is shown consistently
     * for the same place
     *
     * @param placeId The unique ID of the place
     * @param category The category of the place
     * @return A consistent image resource ID for the given place
     */
    public static int getConsistentImageForPlace(String placeId, String category) {
        if (placeId == null) {
            return getRandomImageForCategory(category);
        }

        // Use the hash code of the place ID to select a consistent image
        int hashCode = placeId.hashCode();

        if (category == null) {
            return DEFAULT_IMAGE;
        }

        String lowerCategory = category.toLowerCase();

        if (lowerCategory.contains("hotel") || lowerCategory.contains("accommodation")) {
            return HOTEL_IMAGES[Math.abs(hashCode % HOTEL_IMAGES.length)];
        } else if (lowerCategory.contains("restaurant") || lowerCategory.contains("food") ||
                lowerCategory.contains("catering")) {
            return RESTAURANT_IMAGES[Math.abs(hashCode % RESTAURANT_IMAGES.length)];
        } else if (lowerCategory.contains("attraction") || lowerCategory.contains("tourism") ||
                lowerCategory.contains("sights")) {
            return ATTRACTION_IMAGES[Math.abs(hashCode % ATTRACTION_IMAGES.length)];
        }

        return DEFAULT_IMAGE;
    }
}

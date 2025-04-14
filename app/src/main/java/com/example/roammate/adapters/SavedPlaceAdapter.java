package com.example.roammate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roammate.R;
import com.example.roammate.data.SavedPlaceEntity;

import java.util.ArrayList;
import java.util.List;

//public class SavedPlaceAdapter extends RecyclerView.Adapter<SavedPlaceAdapter.SavedPlaceViewHolder> {
//
//    private Context context;
//    private String category;
//    private List<SavedPlace> savedPlacesList = new ArrayList<>();
//
//    public SavedPlaceAdapter(Context context, String category) {
//        this.context = context;
//        this.category = category;
//
//        // For demonstration, load some mock data
//        loadMockData();
//    }
//
//    @NonNull
//    @Override
//    public SavedPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
//        return new SavedPlaceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SavedPlaceViewHolder holder, int position) {
//        SavedPlace place = savedPlacesList.get(position);
//
//        holder.placeName.setText(place.getName());
//        holder.placeCategory.setText(place.getCategory());
//        holder.placeLocation.setText(place.getAddress());
//        holder.placeRating.setRating(place.getRating());
//        holder.placeDistance.setText(String.format("%.1f km", place.getDistance()));
//
//        // Set placeholder image - in a real app we would load images using Glide or Picasso
//        holder.placeImage.setImageResource(android.R.drawable.ic_menu_gallery);
//
//        // For saved items, we know they're already favorites
//        holder.favoriteButton.setSelected(true);
//        holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
//
//        // Set click listener for favorite button to remove from favorites
//        holder.favoriteButton.setOnClickListener(v -> {
//            // Remove from favorites
//            removeItem(position);
//
//            // In a real app, we would also update the database
//        });
//
//        // Set click listener for the entire item
//        holder.itemView.setOnClickListener(v -> {
//            // Navigate to place details
//            // Implementation will come later
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return savedPlacesList.size();
//    }
//
//    private void removeItem(int position) {
//        savedPlacesList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, savedPlacesList.size());
//    }
//
//    // This is just for demonstration, would be replaced with actual data loading from database
//    private void loadMockData() {
//        // Generate some sample data based on category
//        // In a real app, this would load from the Room database
//        for (int i = 1; i <= 5; i++) {
//            savedPlacesList.add(new SavedPlace(
//                    "id" + i,
//                    "Saved " + category + " " + i,
//                    category,
//                    "Address for " + category + " " + i,
//                    (float) (3.0 + Math.random() * 2.0), // Random rating between 3.0 and 5.0
//                    Math.random() * 5.0, // Random distance between 0 and 5.0 km
//                    ""
//            ));
//        }
//    }
//
//    static class SavedPlaceViewHolder extends RecyclerView.ViewHolder {
//        ImageView placeImage;
//        TextView placeName;
//        TextView placeCategory;
//        TextView placeLocation;
//        RatingBar placeRating;
//        TextView placeDistance;
//        ImageButton favoriteButton;
//
//        public SavedPlaceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            placeImage = itemView.findViewById(R.id.place_image);
//            placeName = itemView.findViewById(R.id.place_name);
//            placeCategory = itemView.findViewById(R.id.place_category);
//            placeLocation = itemView.findViewById(R.id.place_location);
//            placeRating = itemView.findViewById(R.id.place_rating);
//            placeDistance = itemView.findViewById(R.id.place_distance);
//            favoriteButton = itemView.findViewById(R.id.favorite_button);
//        }
//    }
//
//    // SavedPlace model class
//    public static class SavedPlace {
//        private String id;
//        private String name;
//        private String category;
//        private String address;
//        private float rating;
//        private double distance;
//        private String imageUrl;
//
//        public SavedPlace(String id, String name, String category, String address,
//                          float rating, double distance, String imageUrl) {
//            this.id = id;
//            this.name = name;
//            this.category = category;
//            this.address = address;
//            this.rating = rating;
//            this.distance = distance;
//            this.imageUrl = imageUrl;
//        }
//
//        // Getters
//        public String getId() { return id; }
//        public String getName() { return name; }
//        public String getCategory() { return category; }
//        public String getAddress() { return address; }
//        public float getRating() { return rating; }
//        public double getDistance() { return distance; }
//        public String getImageUrl() { return imageUrl; }
//    }
//}

public class SavedPlaceAdapter extends RecyclerView.Adapter<SavedPlaceAdapter.SavedPlaceViewHolder> {

    private final Context context;
    private List<SavedPlaceEntity> savedPlacesList = new ArrayList<>();
    private final OnPlaceActionListener listener;

    /**
     * Interface for saved place actions
     */
    public interface OnPlaceActionListener {
        void onPlaceClick(SavedPlaceEntity place);
        void onPlaceRemove(SavedPlaceEntity place);
    }

    /**
     * Constructor
     */
    public SavedPlaceAdapter(Context context, OnPlaceActionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SavedPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlaceViewHolder holder, int position) {
        SavedPlaceEntity place = savedPlacesList.get(position);

        holder.placeName.setText(place.getName());

        // Get category display name
        String categoryDisplay = getCategoryDisplayName(place.getCategory());
        holder.placeCategory.setText(categoryDisplay);

        holder.placeLocation.setText(place.getAddress());
        holder.placeRating.setRating(place.getRating());

        // For saved places, no distance is shown
        holder.placeDistance.setVisibility(View.GONE);

        // Load image with Glide
        if (place.getImageUrl() != null && !place.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(place.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
                    .into(holder.placeImage);
        } else {
            // Use a category-based placeholder if no image
            int placeholderResId = getCategoryPlaceholder(place.getCategory());
            holder.placeImage.setImageResource(placeholderResId);
        }

        // Set favorite button to "remove" state
        holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
        holder.favoriteButton.setSelected(true);

        // Set click listener for favorite button to remove from favorites
        holder.favoriteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlaceRemove(place);
            }

            // Remove item from the adapter
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                savedPlacesList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });

        // Set click listener for the entire item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlaceClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedPlacesList.size();
    }

    /**
     * Update the saved places list
     */
    public void setPlaces(List<SavedPlaceEntity> places) {
        this.savedPlacesList = places != null ? places : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Get the current list of places
     */
    public List<SavedPlaceEntity> getPlaces() {
        return savedPlacesList;
    }

    /**
     * Convert category string to display name
     */
    private String getCategoryDisplayName(String category) {
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

    /**
     * Get placeholder image based on category
     */
    private int getCategoryPlaceholder(String category) {
        if (category == null) {
            return R.drawable.ic_placeholder_image;
        }

        if (category.contains("hotel") || category.contains("accommodation")) {
            return R.drawable.ic_hotel_placeholder;
        } else if (category.contains("restaurant") || category.contains("food") || category.contains("catering")) {
            return R.drawable.ic_restaurant_placeholder;
        } else if (category.contains("attraction") || category.contains("tourism")) {
            return R.drawable.ic_attraction_placeholder;
        } else {
            return R.drawable.ic_placeholder_image;
        }
    }

    /**
     * ViewHolder class
     */
    static class SavedPlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeCategory;
        TextView placeLocation;
        RatingBar placeRating;
        TextView placeDistance;
        ImageButton favoriteButton;

        public SavedPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            placeCategory = itemView.findViewById(R.id.place_category);
            placeLocation = itemView.findViewById(R.id.place_location);
            placeRating = itemView.findViewById(R.id.place_rating);
            placeDistance = itemView.findViewById(R.id.place_distance);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}

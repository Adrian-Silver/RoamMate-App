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
import com.example.roammate.data.model.Place;

import java.util.ArrayList;
import java.util.List;

//public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
//
//    private Context context;
//    private String category;
//    private List<Place> placesList = new ArrayList<>();
//
//    public PlaceAdapter(Context context, String category) {
//        this.context = context;
//        this.category = category;
//
//        // For now, add some mock data
//        loadMockData();
//    }
//
//    @NonNull
//    @Override
//    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_place_card, parent, false);
//        return new PlaceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
//        Place place = placesList.get(position);
//
//        holder.placeName.setText(place.getName());
//        holder.placeLocation.setText(place.getLocation());
//        holder.placeRating.setRating(place.getRating());
//
//        // Set placeholder image - in a real app we would load images using Glide or Picasso
//        holder.placeImage.setImageResource(android.R.drawable.ic_menu_gallery);
//
//        // Set click listener for favorite button
//        holder.favoriteButton.setOnClickListener(v -> {
//            // Toggle favorite state
//            place.setFavorite(!place.isFavorite());
//            holder.favoriteButton.setSelected(place.isFavorite());
//
//            // Update image based on favorite state
//            if (place.isFavorite()) {
//                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
//                // Save to favorites in database
//            } else {
//                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
//                // Remove from favorites in database
//            }
//        });
//
//        // Set favorite button state based on place favorite status
//        holder.favoriteButton.setSelected(place.isFavorite());
//        holder.favoriteButton.setImageResource(place.isFavorite() ?
//                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
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
//        return placesList.size();
//    }
//
//    // This is just for demonstration, would be replaced with actual data loading
//    private void loadMockData() {
//        // Generate some sample data based on category
//        for (int i = 1; i <= 10; i++) {
//            placesList.add(new Place(
//                    "id" + i,
//                    category.substring(0, 1).toUpperCase() + category.substring(1) + " " + i,
//                    "Location " + i,
//                    (float) (2.5 + Math.random() * 2.5), // Random rating between 2.5 and 5.0
//                    "",
//                    false
//            ));
//        }
//    }
//
//    static class PlaceViewHolder extends RecyclerView.ViewHolder {
//        ImageView placeImage;
//        TextView placeName;
//        TextView placeLocation;
//        RatingBar placeRating;
//        ImageButton favoriteButton;
//
//        public PlaceViewHolder(@NonNull View itemView) {
//            super(itemView);
//            placeImage = itemView.findViewById(R.id.place_image);
//            placeName = itemView.findViewById(R.id.place_name);
//            placeLocation = itemView.findViewById(R.id.place_location);
//            placeRating = itemView.findViewById(R.id.place_rating);
//            favoriteButton = itemView.findViewById(R.id.favorite_button);
//        }
//    }
//
//    // Place model class
//    public static class Place {
//        private String id;
//        private String name;
//        private String location;
//        private float rating;
//        private String imageUrl;
//        private boolean isFavorite;
//
//        public Place(String id, String name, String location, float rating, String imageUrl, boolean isFavorite) {
//            this.id = id;
//            this.name = name;
//            this.location = location;
//            this.rating = rating;
//            this.imageUrl = imageUrl;
//            this.isFavorite = isFavorite;
//        }
//
//        // Getters and setters
//        public String getId() { return id; }
//
//        public String getName() { return name; }
//
//        public String getLocation() { return location; }
//
//        public float getRating() { return rating; }
//
//        public String getImageUrl() { return imageUrl; }
//
//        public boolean isFavorite() { return isFavorite; }
//
//        public void setFavorite(boolean favorite) { isFavorite = favorite; }
//    }
//}

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private final Context context;
    private final String category;
    private List<Place> placesList = new ArrayList<>();
    private OnPlaceClickListener listener;
    private OnPlaceSaveListener saveListener;

    /**
     * Interface for click events
     */
    public interface OnPlaceClickListener {
        void onPlaceClick(Place place);
    }

    /**
     * Interface for save/unsave events
     */
    public interface OnPlaceSaveListener {
        void onPlaceSave(Place place);
        void onPlaceUnsave(Place place);
    }

    /**
     * Constructor
     */
    public PlaceAdapter(Context context, String category) {
        this.context = context;
        this.category = category;
    }

    /**
     * Set click listener
     */
    public void setOnPlaceClickListener(OnPlaceClickListener listener) {
        this.listener = listener;
    }

    /**
     * Set save listener
     */
    public void setOnPlaceSaveListener(OnPlaceSaveListener listener) {
        this.saveListener = listener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place_card, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placesList.get(position);

        holder.placeName.setText(place.getName());
        holder.placeLocation.setText(place.getSimpleAddress());
        holder.placeRating.setRating(place.getRating());

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

        // Set favorite button state
        holder.favoriteButton.setSelected(place.isSaved());
        holder.favoriteButton.setImageResource(place.isSaved() ?
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        // Set click listener for favorite button
        holder.favoriteButton.setOnClickListener(v -> {
            boolean isCurrentlySaved = place.isSaved();
            place.setSaved(!isCurrentlySaved);
            holder.favoriteButton.setSelected(place.isSaved());
            holder.favoriteButton.setImageResource(place.isSaved() ?
                    android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

            // Notify listener
            if (saveListener != null) {
                if (place.isSaved()) {
                    saveListener.onPlaceSave(place);
                } else {
                    saveListener.onPlaceUnsave(place);
                }
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
        return placesList.size();
    }

    /**
     * Update the places list
     */
    public void setPlaces(List<Place> places) {
        this.placesList = places;
        notifyDataSetChanged();
    }

    /**
     * Get the current list of places
     */
    public List<Place> getPlaces() {
        return placesList;
    }

    /**
     * Get a placeholder image based on category
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
    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeLocation;
        RatingBar placeRating;
        ImageButton favoriteButton;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            placeLocation = itemView.findViewById(R.id.place_location);
            placeRating = itemView.findViewById(R.id.place_rating);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}


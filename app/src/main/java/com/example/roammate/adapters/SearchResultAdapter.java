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
//import com.example.roammate.SearchResultsFragment.SearchResult;
import com.example.roammate.data.model.Place;

import java.util.ArrayList;
import java.util.List;

//public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
//
//    private Context context;
//    private List<SearchResult> searchResults;
//
//    public SearchResultAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setSearchResults(SearchResult[] results) {
//        this.searchResults = Arrays.asList(results);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
//        return new SearchResultViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
//        SearchResult result = searchResults.get(position);
//
//        holder.placeName.setText(result.getName());
//        holder.placeCategory.setText(result.getCategory());
//        holder.placeLocation.setText(result.getAddress());
//        holder.placeRating.setRating(result.getRating());
//        holder.placeDistance.setText(String.format("%.1f km", result.getDistance()));
//
//        // Set placeholder image - in a real app we would load images using Glide or Picasso
//        holder.placeImage.setImageResource(android.R.drawable.ic_menu_gallery);
//
//        // Set click listener for favorite button
//        holder.favoriteButton.setOnClickListener(v -> {
//            // Toggle favorite state
//            boolean isFavorite = holder.favoriteButton.isSelected();
//            holder.favoriteButton.setSelected(!isFavorite);
//
//            // Update image based on selected state
//            if (holder.favoriteButton.isSelected()) {
//                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
//                // Save to favorites in database
//            } else {
//                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
//                // Remove from favorites in database
//            }
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
//        return searchResults != null ? searchResults.size() : 0;
//    }
//
//    static class SearchResultViewHolder extends RecyclerView.ViewHolder {
//        ImageView placeImage;
//        TextView placeName;
//        TextView placeCategory;
//        TextView placeLocation;
//        RatingBar placeRating;
//        TextView placeDistance;
//        ImageButton favoriteButton;
//
//        public SearchResultViewHolder(@NonNull View itemView) {
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
//}

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private Context context;
    private List<Place> searchResults = new ArrayList<>();
    private OnPlaceClickListener listener;
    private OnPlaceSaveListener saveListener;

    public interface OnPlaceClickListener {
        void onPlaceClick(Place place);
    }

    public interface OnPlaceSaveListener {
        void onPlaceSave(Place place);
        void onPlaceUnsave(Place place);
    }

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setOnPlaceClickListener(OnPlaceClickListener listener) {
        this.listener = listener;
    }

    public void setOnPlaceSaveListener(OnPlaceSaveListener listener) {
        this.saveListener = listener;
    }

    public void setSearchResults(List<Place> results) {
        this.searchResults = results != null ? results : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Place place = searchResults.get(position);

        holder.placeName.setText(place.getName());
        holder.placeCategory.setText(place.getCategoryDisplayName());
        holder.placeLocation.setText(place.getSimpleAddress());
        holder.placeRating.setRating(place.getRating());
        holder.placeDistance.setText(place.getDistanceText());

        // Load image with Glide
        if (place.getImageUrl() != null && !place.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(place.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
                    .into(holder.placeImage);
        } else {
            // Set a default placeholder
            holder.placeImage.setImageResource(R.drawable.ic_placeholder_image);
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
        return searchResults.size();
    }

    static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeCategory;
        TextView placeLocation;
        RatingBar placeRating;
        TextView placeDistance;
        ImageButton favoriteButton;

        public SearchResultViewHolder(@NonNull View itemView) {
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

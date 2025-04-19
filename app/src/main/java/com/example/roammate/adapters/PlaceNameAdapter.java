package com.example.roammate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roammate.R;
import com.example.roammate.data.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying place name search results
 */
public class PlaceNameAdapter extends RecyclerView.Adapter<PlaceNameAdapter.PlaceNameViewHolder> {

    private final Context context;
    private List<Place> places = new ArrayList<>();
    private final OnPlaceClickListener listener;

    /**
     * Interface for click events
     */
    public interface OnPlaceClickListener {
        void onPlaceClick(Place place);
    }

    /**
     * Constructor
     */
    public PlaceNameAdapter(Context context, OnPlaceClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Update the places list
     */
    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place_name, parent, false);
        return new PlaceNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceNameViewHolder holder, int position) {
        Place place = places.get(position);

        // Set place name
        holder.placeName.setText(place.getName());

        // Set location details
        StringBuilder locationBuilder = new StringBuilder();

//        if (place.getCity() != null && !place.getCity().isEmpty() &&
//                !place.getCity().equals(place.getName())) {
//            locationBuilder.append(place.getCity());
//        }

//        if (place.getState() != null && !place.getState().isEmpty()) {
//            if (locationBuilder.length() > 0) {
//                locationBuilder.append(", ");
//            }
//            locationBuilder.append(place.getState());
//        }

//        if (place.getCountry() != null && !place.getCountry().isEmpty()) {
//            if (locationBuilder.length() > 0) {
//                locationBuilder.append(", ");
//            }
//            locationBuilder.append(place.getCountry());
//        }
//
//        holder.placeLocation.setText(locationBuilder.toString());

        if (place.getState() != null && !place.getState().isEmpty()) {
            locationBuilder.append(place.getState());
        }

        if (place.getCountry() != null && !place.getCountry().isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(place.getCountry());
        }

        // If no location info is available, use formatted address
        if (locationBuilder.length() == 0 && place.getFormattedAddress() != null) {
            locationBuilder.append(place.getFormattedAddress());
        }

        holder.placeLocation.setText(locationBuilder.toString());

        // Set appropriate icon based on result type if available
        if (place.getCategory() != null) {
            if (place.getCategory().contains("administrative")) {
                holder.placeIcon.setImageResource(android.R.drawable.ic_dialog_map);
            } else if (place.getCategory().contains("populated")) {
                holder.placeIcon.setImageResource(android.R.drawable.ic_menu_compass);
            } else {
                holder.placeIcon.setImageResource(android.R.drawable.ic_menu_mylocation);
            }
        } else {
            holder.placeIcon.setImageResource(android.R.drawable.ic_menu_mylocation);
        }

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlaceClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    /**
     * ViewHolder class
     */
    static class PlaceNameViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeLocation;
        ImageView placeIcon;

        public PlaceNameViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeLocation = itemView.findViewById(R.id.place_location);
            placeIcon = itemView.findViewById(R.id.place_icon);
        }
    }
}


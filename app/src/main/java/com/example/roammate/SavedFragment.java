package com.example.roammate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roammate.adapters.SavedPlaceAdapter;
import com.example.roammate.data.SavedPlaceEntity;
import com.example.roammate.viewmodel.SavedPlaceViewModel;

import java.util.List;

//public class SavedFragment extends Fragment {
//
//    private LinearLayout attractionsHeader;
//    private LinearLayout hotelsHeader;
//    private LinearLayout restaurantsHeader;
//    private LinearLayout tipsHeader;
//
//    private ImageView attractionsExpandIcon;
//    private ImageView hotelsExpandIcon;
//    private ImageView restaurantsExpandIcon;
//    private ImageView tipsExpandIcon;
//
//    private RecyclerView attractionsRecyclerView;
//    private RecyclerView hotelsRecyclerView;
//    private RecyclerView restaurantsRecyclerView;
//    private RecyclerView tipsRecyclerView;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_saved, container, false);
//
//        // Initialize views
//        attractionsHeader = root.findViewById(R.id.attractions_header);
//        hotelsHeader = root.findViewById(R.id.hotels_header);
//        restaurantsHeader = root.findViewById(R.id.restaurants_header);
//        tipsHeader = root.findViewById(R.id.tips_header);
//
//        attractionsExpandIcon = root.findViewById(R.id.attractions_expand_icon);
//        hotelsExpandIcon = root.findViewById(R.id.hotels_expand_icon);
//        restaurantsExpandIcon = root.findViewById(R.id.restaurants_expand_icon);
//        tipsExpandIcon = root.findViewById(R.id.tips_expand_icon);
//
//        attractionsRecyclerView = root.findViewById(R.id.attractions_recycler_view);
//        hotelsRecyclerView = root.findViewById(R.id.hotels_recycler_view);
//        restaurantsRecyclerView = root.findViewById(R.id.restaurants_recycler_view);
//        tipsRecyclerView = root.findViewById(R.id.tips_recycler_view);
//
//        // Set up RecyclerViews
//        setupRecyclerViews();
//
//        // Set up click listeners for headers
//        setupHeaderClickListeners();
//
//        return root;
//    }
//
//    private void setupRecyclerViews() {
//        // Set layout managers for RecyclerViews
//        attractionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Set adapters for RecyclerViews (these will be implemented later)
//        SavedPlaceAdapter attractionsAdapter = new SavedPlaceAdapter(getContext(), "attractions");
//        attractionsRecyclerView.setAdapter(attractionsAdapter);
//
//        SavedPlaceAdapter hotelsAdapter = new SavedPlaceAdapter(getContext(), "hotels");
//        hotelsRecyclerView.setAdapter(hotelsAdapter);
//
//        SavedPlaceAdapter restaurantsAdapter = new SavedPlaceAdapter(getContext(), "restaurants");
//        restaurantsRecyclerView.setAdapter(restaurantsAdapter);
//
//        SavedPlaceAdapter tipsAdapter = new SavedPlaceAdapter(getContext(), "tips");
//        tipsRecyclerView.setAdapter(tipsAdapter);
//    }
//
//    private void setupHeaderClickListeners() {
//        // Attractions header click listener
//        attractionsHeader.setOnClickListener(v -> {
//            toggleSection(attractionsRecyclerView, attractionsExpandIcon);
//        });
//
//        // Hotels header click listener
//        hotelsHeader.setOnClickListener(v -> {
//            toggleSection(hotelsRecyclerView, hotelsExpandIcon);
//        });
//
//        // Restaurants header click listener
//        restaurantsHeader.setOnClickListener(v -> {
//            toggleSection(restaurantsRecyclerView, restaurantsExpandIcon);
//        });
//
//        // Tips header click listener
//        tipsHeader.setOnClickListener(v -> {
//            toggleSection(tipsRecyclerView, tipsExpandIcon);
//        });
//    }
//
//    private void toggleSection(RecyclerView recyclerView, ImageView expandIcon) {
//        // Toggle RecyclerView visibility
//        if (recyclerView.getVisibility() == View.VISIBLE) {
//            recyclerView.setVisibility(View.GONE);
//            expandIcon.setImageResource(android.R.drawable.arrow_down_float);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            expandIcon.setImageResource(android.R.drawable.arrow_up_float);
//        }
//    }
//}

public class SavedFragment extends Fragment {

    private LinearLayout attractionsHeader;
    private LinearLayout hotelsHeader;
    private LinearLayout restaurantsHeader;
    private LinearLayout tipsHeader;

    private ImageView attractionsExpandIcon;
    private ImageView hotelsExpandIcon;
    private ImageView restaurantsExpandIcon;
    private ImageView tipsExpandIcon;

    private RecyclerView attractionsRecyclerView;
    private RecyclerView hotelsRecyclerView;
    private RecyclerView restaurantsRecyclerView;
    private RecyclerView tipsRecyclerView;

    private TextView emptyAttractions;
    private TextView emptyHotels;
    private TextView emptyRestaurants;
    private TextView emptyTips;

    private SavedPlaceAdapter attractionsAdapter;
    private SavedPlaceAdapter hotelsAdapter;
    private SavedPlaceAdapter restaurantsAdapter;
    private SavedPlaceAdapter tipsAdapter;

    private SavedPlaceViewModel savedPlaceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        // Initialize view model
        savedPlaceViewModel = new ViewModelProvider(this).get(SavedPlaceViewModel.class);

        // Initialize views
        initializeViews(root);

        // Set up RecyclerViews
        setupRecyclerViews();

        // Set up click listeners for headers
        setupHeaderClickListeners();

        // Load saved places
        loadSavedPlaces();

        return root;
    }

    /**
     * Initialize views from layout
     */
    private void initializeViews(View root) {
        // Headers
        attractionsHeader = root.findViewById(R.id.attractions_header);
        hotelsHeader = root.findViewById(R.id.hotels_header);
        restaurantsHeader = root.findViewById(R.id.restaurants_header);
        tipsHeader = root.findViewById(R.id.tips_header);

        // Expand icons
        attractionsExpandIcon = root.findViewById(R.id.attractions_expand_icon);
        hotelsExpandIcon = root.findViewById(R.id.hotels_expand_icon);
        restaurantsExpandIcon = root.findViewById(R.id.restaurants_expand_icon);
        tipsExpandIcon = root.findViewById(R.id.tips_expand_icon);

        // RecyclerViews
        attractionsRecyclerView = root.findViewById(R.id.attractions_recycler_view);
        hotelsRecyclerView = root.findViewById(R.id.hotels_recycler_view);
        restaurantsRecyclerView = root.findViewById(R.id.restaurants_recycler_view);
        tipsRecyclerView = root.findViewById(R.id.tips_recycler_view);

        // Empty views (add these to your layout if not already there)
        emptyAttractions = root.findViewById(R.id.empty_attractions);
        emptyHotels = root.findViewById(R.id.empty_hotels);
        emptyRestaurants = root.findViewById(R.id.empty_restaurants);
        emptyTips = root.findViewById(R.id.empty_tips);

        // If empty views are not in your layout, you can handle this differently
        if (emptyAttractions == null || emptyHotels == null ||
                emptyRestaurants == null || emptyTips == null) {
            // Handle the case where empty views are not in layout
        }
    }

    /**
     * Set up RecyclerViews
     */
    private void setupRecyclerViews() {
        // Set layout managers
        attractionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create adapters
        attractionsAdapter = new SavedPlaceAdapter(getContext(), new SavedPlaceAdapter.OnPlaceActionListener() {
            @Override
            public void onPlaceClick(SavedPlaceEntity place) {
                // Navigate to place details
                Toast.makeText(getContext(), "Viewing " + place.getName(), Toast.LENGTH_SHORT).show();
                // Navigation implementation will be added later

                // Navigate to place details
                Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
                startActivity(intent);
            }

            @Override
            public void onPlaceRemove(SavedPlaceEntity place) {
                // Remove from saved places
                savedPlaceViewModel.removeSavedPlace(place.getPlaceId());
                Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        hotelsAdapter = new SavedPlaceAdapter(getContext(), new SavedPlaceAdapter.OnPlaceActionListener() {
            @Override
            public void onPlaceClick(SavedPlaceEntity place) {
                Toast.makeText(getContext(), "Viewing " + place.getName(), Toast.LENGTH_SHORT).show();

                // Navigate to place details
                Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
                startActivity(intent);
            }

            @Override
            public void onPlaceRemove(SavedPlaceEntity place) {
                savedPlaceViewModel.removeSavedPlace(place.getPlaceId());
                Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        restaurantsAdapter = new SavedPlaceAdapter(getContext(), new SavedPlaceAdapter.OnPlaceActionListener() {
            @Override
            public void onPlaceClick(SavedPlaceEntity place) {
                Toast.makeText(getContext(), "Viewing " + place.getName(), Toast.LENGTH_SHORT).show();

                // Navigate to place details
                Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
                startActivity(intent);
            }

            @Override
            public void onPlaceRemove(SavedPlaceEntity place) {
                savedPlaceViewModel.removeSavedPlace(place.getPlaceId());
                Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        tipsAdapter = new SavedPlaceAdapter(getContext(), new SavedPlaceAdapter.OnPlaceActionListener() {
            @Override
            public void onPlaceClick(SavedPlaceEntity place) {
                Toast.makeText(getContext(), "Viewing " + place.getName(), Toast.LENGTH_SHORT).show();

                // Navigate to place details
                Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
                startActivity(intent);
            }

            @Override
            public void onPlaceRemove(SavedPlaceEntity place) {
                savedPlaceViewModel.removeSavedPlace(place.getPlaceId());
                Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        // Set adapters to RecyclerViews
        attractionsRecyclerView.setAdapter(attractionsAdapter);
        hotelsRecyclerView.setAdapter(hotelsAdapter);
        restaurantsRecyclerView.setAdapter(restaurantsAdapter);
        tipsRecyclerView.setAdapter(tipsAdapter);
    }

    /**
     * Set up click listeners for section headers
     */
    private void setupHeaderClickListeners() {
        // Attractions header click listener
        attractionsHeader.setOnClickListener(v -> {
            toggleSection(attractionsRecyclerView, attractionsExpandIcon, emptyAttractions);
        });

        // Hotels header click listener
        hotelsHeader.setOnClickListener(v -> {
            toggleSection(hotelsRecyclerView, hotelsExpandIcon, emptyHotels);
        });

        // Restaurants header click listener
        restaurantsHeader.setOnClickListener(v -> {
            toggleSection(restaurantsRecyclerView, restaurantsExpandIcon, emptyRestaurants);
        });

        // Tips header click listener
        tipsHeader.setOnClickListener(v -> {
            toggleSection(tipsRecyclerView, tipsExpandIcon, emptyTips);
        });
    }

    /**
     * Toggle section visibility
     */
    private void toggleSection(RecyclerView recyclerView, ImageView expandIcon, TextView emptyView) {
        boolean isCurrentlyVisible = recyclerView.getVisibility() == View.VISIBLE;

        // Toggle RecyclerView visibility
        recyclerView.setVisibility(isCurrentlyVisible ? View.GONE : View.VISIBLE);

        // Toggle empty view visibility if it exists
        if (emptyView != null) {
            boolean shouldShowEmpty = !isCurrentlyVisible &&
                    (recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() == 0);
            emptyView.setVisibility(shouldShowEmpty ? View.VISIBLE : View.GONE);
        }

        // Update expand icon
        expandIcon.setImageResource(isCurrentlyVisible ?
                android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
    }

    /**
     * Load saved places from the database
     */
    private void loadSavedPlaces() {
        // Load attractions
        savedPlaceViewModel.getSavedPlaces("attraction").observe(getViewLifecycleOwner(), places -> {
            attractionsAdapter.setPlaces(places);
            updateEmptyView(places, emptyAttractions, attractionsRecyclerView);
        });

        // Load hotels
        savedPlaceViewModel.getSavedPlaces("hotel").observe(getViewLifecycleOwner(), places -> {
            hotelsAdapter.setPlaces(places);
            updateEmptyView(places, emptyHotels, hotelsRecyclerView);
        });

        // Load restaurants
        savedPlaceViewModel.getSavedPlaces("restaurant").observe(getViewLifecycleOwner(), places -> {
            restaurantsAdapter.setPlaces(places);
            updateEmptyView(places, emptyRestaurants, restaurantsRecyclerView);
        });

        // Load tips
        savedPlaceViewModel.getSavedPlaces("tip").observe(getViewLifecycleOwner(), places -> {
            tipsAdapter.setPlaces(places);
            updateEmptyView(places, emptyTips, tipsRecyclerView);
        });
    }

    /**
     * Update empty view visibility
     */
    private void updateEmptyView(List<SavedPlaceEntity> places, TextView emptyView, RecyclerView recyclerView) {
        if (emptyView == null) return;

        boolean isEmpty = places == null || places.isEmpty();
        boolean isExpanded = recyclerView.getVisibility() == View.VISIBLE;

        // Show empty view only if section is expanded and has no items
        emptyView.setVisibility(isEmpty && isExpanded ? View.VISIBLE : View.GONE);
    }
}
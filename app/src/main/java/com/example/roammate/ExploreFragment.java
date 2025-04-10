package com.example.roammate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roammate.adapters.PlaceAdapter;
import com.google.android.material.chip.Chip;


public class ExploreFragment extends Fragment {

    private RecyclerView restaurantsRecyclerView;
    private RecyclerView attractionsRecyclerView;
    private RecyclerView hotelsRecyclerView;
    private RecyclerView tipsRecyclerView;

    private CardView searchBar;
    private Chip chipAttractions;
    private Chip chipHotels;
    private Chip chipRestaurants;
    private Chip chipTips;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        // Initialize views
        searchBar = root.findViewById(R.id.search_bar);
        chipAttractions = root.findViewById(R.id.chip_attractions);
        chipHotels = root.findViewById(R.id.chip_hotels);
        chipRestaurants = root.findViewById(R.id.chip_restaurants);
        chipTips = root.findViewById(R.id.chip_tips);

        restaurantsRecyclerView = root.findViewById(R.id.restaurants_recycler_view);
        attractionsRecyclerView = root.findViewById(R.id.attractions_recycler_view);
        hotelsRecyclerView = root.findViewById(R.id.hotels_recycler_view);
        tipsRecyclerView = root.findViewById(R.id.tips_recycler_view);

        // Set up RecyclerViews
        setupRecyclerViews();

        // Set up click listeners
        setupClickListeners();

        return root;
    }

    private void setupRecyclerViews() {
        // Set layout managers for RecyclerViews
        LinearLayoutManager restaurantsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        restaurantsRecyclerView.setLayoutManager(restaurantsLayoutManager);

        LinearLayoutManager attractionsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        attractionsRecyclerView.setLayoutManager(attractionsLayoutManager);

        LinearLayoutManager hotelsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hotelsRecyclerView.setLayoutManager(hotelsLayoutManager);

        LinearLayoutManager tipsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tipsRecyclerView.setLayoutManager(tipsLayoutManager);

        // Set adapters for RecyclerViews (these will be implemented later)
        PlaceAdapter restaurantsAdapter = new PlaceAdapter(getContext(), "restaurants");
        restaurantsRecyclerView.setAdapter(restaurantsAdapter);

        PlaceAdapter attractionsAdapter = new PlaceAdapter(getContext(), "attractions");
        attractionsRecyclerView.setAdapter(attractionsAdapter);

        PlaceAdapter hotelsAdapter = new PlaceAdapter(getContext(), "hotels");
        hotelsRecyclerView.setAdapter(hotelsAdapter);

        PlaceAdapter tipsAdapter = new PlaceAdapter(getContext(), "tips");
        tipsRecyclerView.setAdapter(tipsAdapter);
    }

    private void setupClickListeners() {
        // Navigate to search fragment when search bar is clicked
        searchBar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigation_search);
        });

        // Category chips click listeners
        chipAttractions.setOnClickListener(v -> {
            // Navigate to Search fragment with attractions filter
            Bundle args = new Bundle();
            args.putString("category", "attractions");
            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
        });

        chipHotels.setOnClickListener(v -> {
            // Navigate to Search fragment with hotels filter
            Bundle args = new Bundle();
            args.putString("category", "hotels");
            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
        });

        chipRestaurants.setOnClickListener(v -> {
            // Navigate to Search fragment with restaurants filter
            Bundle args = new Bundle();
            args.putString("category", "restaurants");
            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
        });

        chipTips.setOnClickListener(v -> {
            // Navigate to Search fragment with tips filter
            Bundle args = new Bundle();
            args.putString("category", "tips");
            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
        });
    }
}
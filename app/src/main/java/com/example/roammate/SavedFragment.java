package com.example.roammate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.roammate.adapters.SavedPlaceAdapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_saved, container, false);

        // Initialize views
        attractionsHeader = root.findViewById(R.id.attractions_header);
        hotelsHeader = root.findViewById(R.id.hotels_header);
        restaurantsHeader = root.findViewById(R.id.restaurants_header);
        tipsHeader = root.findViewById(R.id.tips_header);

        attractionsExpandIcon = root.findViewById(R.id.attractions_expand_icon);
        hotelsExpandIcon = root.findViewById(R.id.hotels_expand_icon);
        restaurantsExpandIcon = root.findViewById(R.id.restaurants_expand_icon);
        tipsExpandIcon = root.findViewById(R.id.tips_expand_icon);

        attractionsRecyclerView = root.findViewById(R.id.attractions_recycler_view);
        hotelsRecyclerView = root.findViewById(R.id.hotels_recycler_view);
        restaurantsRecyclerView = root.findViewById(R.id.restaurants_recycler_view);
        tipsRecyclerView = root.findViewById(R.id.tips_recycler_view);

        // Set up RecyclerViews
        setupRecyclerViews();

        // Set up click listeners for headers
        setupHeaderClickListeners();

        return root;
    }

    private void setupRecyclerViews() {
        // Set layout managers for RecyclerViews
        attractionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set adapters for RecyclerViews (these will be implemented later)
        SavedPlaceAdapter attractionsAdapter = new SavedPlaceAdapter(getContext(), "attractions");
        attractionsRecyclerView.setAdapter(attractionsAdapter);

        SavedPlaceAdapter hotelsAdapter = new SavedPlaceAdapter(getContext(), "hotels");
        hotelsRecyclerView.setAdapter(hotelsAdapter);

        SavedPlaceAdapter restaurantsAdapter = new SavedPlaceAdapter(getContext(), "restaurants");
        restaurantsRecyclerView.setAdapter(restaurantsAdapter);

        SavedPlaceAdapter tipsAdapter = new SavedPlaceAdapter(getContext(), "tips");
        tipsRecyclerView.setAdapter(tipsAdapter);
    }

    private void setupHeaderClickListeners() {
        // Attractions header click listener
        attractionsHeader.setOnClickListener(v -> {
            toggleSection(attractionsRecyclerView, attractionsExpandIcon);
        });

        // Hotels header click listener
        hotelsHeader.setOnClickListener(v -> {
            toggleSection(hotelsRecyclerView, hotelsExpandIcon);
        });

        // Restaurants header click listener
        restaurantsHeader.setOnClickListener(v -> {
            toggleSection(restaurantsRecyclerView, restaurantsExpandIcon);
        });

        // Tips header click listener
        tipsHeader.setOnClickListener(v -> {
            toggleSection(tipsRecyclerView, tipsExpandIcon);
        });
    }

    private void toggleSection(RecyclerView recyclerView, ImageView expandIcon) {
        // Toggle RecyclerView visibility
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            expandIcon.setImageResource(android.R.drawable.arrow_down_float);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            expandIcon.setImageResource(android.R.drawable.arrow_up_float);
        }
    }
}
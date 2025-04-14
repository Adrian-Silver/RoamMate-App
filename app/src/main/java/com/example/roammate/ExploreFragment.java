package com.example.roammate;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.roammate.adapters.PlaceAdapter;
import com.example.roammate.data.model.Place;
import com.example.roammate.util.Resource;
import com.example.roammate.viewmodel.PlaceViewModel;
import com.google.android.material.chip.Chip;

import java.util.List;


//public class ExploreFragment extends Fragment {
//
//    private RecyclerView restaurantsRecyclerView;
//    private RecyclerView attractionsRecyclerView;
//    private RecyclerView hotelsRecyclerView;
//    private RecyclerView tipsRecyclerView;
//
//    private CardView searchBar;
//    private Chip chipAttractions;
//    private Chip chipHotels;
//    private Chip chipRestaurants;
//    private Chip chipTips;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_explore, container, false);
//
//        // Initialize views
//        searchBar = root.findViewById(R.id.search_bar);
//        chipAttractions = root.findViewById(R.id.chip_attractions);
//        chipHotels = root.findViewById(R.id.chip_hotels);
//        chipRestaurants = root.findViewById(R.id.chip_restaurants);
//        chipTips = root.findViewById(R.id.chip_tips);
//
//        restaurantsRecyclerView = root.findViewById(R.id.restaurants_recycler_view);
//        attractionsRecyclerView = root.findViewById(R.id.attractions_recycler_view);
//        hotelsRecyclerView = root.findViewById(R.id.hotels_recycler_view);
//        tipsRecyclerView = root.findViewById(R.id.tips_recycler_view);
//
//        // Set up RecyclerViews
//        setupRecyclerViews();
//
//        // Set up click listeners
//        setupClickListeners();
//
//        return root;
//    }
//
//    private void setupRecyclerViews() {
//        // Set layout managers for RecyclerViews
//        LinearLayoutManager restaurantsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        restaurantsRecyclerView.setLayoutManager(restaurantsLayoutManager);
//
//        LinearLayoutManager attractionsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        attractionsRecyclerView.setLayoutManager(attractionsLayoutManager);
//
//        LinearLayoutManager hotelsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        hotelsRecyclerView.setLayoutManager(hotelsLayoutManager);
//
//        LinearLayoutManager tipsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        tipsRecyclerView.setLayoutManager(tipsLayoutManager);
//
//        // Set adapters for RecyclerViews (these will be implemented later)
//        PlaceAdapter restaurantsAdapter = new PlaceAdapter(getContext(), "restaurants");
//        restaurantsRecyclerView.setAdapter(restaurantsAdapter);
//
//        PlaceAdapter attractionsAdapter = new PlaceAdapter(getContext(), "attractions");
//        attractionsRecyclerView.setAdapter(attractionsAdapter);
//
//        PlaceAdapter hotelsAdapter = new PlaceAdapter(getContext(), "hotels");
//        hotelsRecyclerView.setAdapter(hotelsAdapter);
//
//        PlaceAdapter tipsAdapter = new PlaceAdapter(getContext(), "tips");
//        tipsRecyclerView.setAdapter(tipsAdapter);
//    }
//
//    private void setupClickListeners() {
//        // Navigate to search fragment when search bar is clicked
//        searchBar.setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.navigation_search);
//        });
//
//        // Category chips click listeners
//        chipAttractions.setOnClickListener(v -> {
//            // Navigate to Search fragment with attractions filter
//            Bundle args = new Bundle();
//            args.putString("category", "attractions");
//            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
//        });
//
//        chipHotels.setOnClickListener(v -> {
//            // Navigate to Search fragment with hotels filter
//            Bundle args = new Bundle();
//            args.putString("category", "hotels");
//            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
//        });
//
//        chipRestaurants.setOnClickListener(v -> {
//            // Navigate to Search fragment with restaurants filter
//            Bundle args = new Bundle();
//            args.putString("category", "restaurants");
//            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
//        });
//
//        chipTips.setOnClickListener(v -> {
//            // Navigate to Search fragment with tips filter
//            Bundle args = new Bundle();
//            args.putString("category", "tips");
//            Navigation.findNavController(v).navigate(R.id.navigation_search, args);
//        });
//    }
//}

public class ExploreFragment extends Fragment implements PlaceAdapter.OnPlaceClickListener, PlaceAdapter.OnPlaceSaveListener {

    private RecyclerView restaurantsRecyclerView;
    private RecyclerView attractionsRecyclerView;
    private RecyclerView hotelsRecyclerView;
    private RecyclerView tipsRecyclerView;

    private CardView searchBar;
    private Chip chipAttractions;
    private Chip chipHotels;
    private Chip chipRestaurants;
    private Chip chipTips;

    private PlaceViewModel placeViewModel;

    // Default location ID for popular destination (Cape Town in this example)
    private static final String DEFAULT_LOCATION_ID = "5137bf06e9ce6a3240592c859be7e3f640c0f00103f901de97f20100000000c00207920307383030312b7a61";

    // Location coordinates for nearby search
    private double userLatitude = -33.9258; // Default to Cape Town
    private double userLongitude = 18.4232;

    // Adapters
    private PlaceAdapter restaurantsAdapter;
    private PlaceAdapter attractionsAdapter;
    private PlaceAdapter hotelsAdapter;
    private PlaceAdapter tipsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        // Get ViewModel
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);

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

        // Try to get user's location
        getUserLocation();

        // Load data
        loadData();

        return root;
    }

    /**
     * Set up RecyclerViews with adapters
     */
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

        // Create adapters
        restaurantsAdapter = new PlaceAdapter(getContext(), "restaurants");
        restaurantsAdapter.setOnPlaceClickListener(this);
        restaurantsAdapter.setOnPlaceSaveListener(this);
        restaurantsRecyclerView.setAdapter(restaurantsAdapter);

        attractionsAdapter = new PlaceAdapter(getContext(), "attractions");
        attractionsAdapter.setOnPlaceClickListener(this);
        attractionsAdapter.setOnPlaceSaveListener(this);
        attractionsRecyclerView.setAdapter(attractionsAdapter);

        hotelsAdapter = new PlaceAdapter(getContext(), "hotels");
        hotelsAdapter.setOnPlaceClickListener(this);
        hotelsAdapter.setOnPlaceSaveListener(this);
        hotelsRecyclerView.setAdapter(hotelsAdapter);

        tipsAdapter = new PlaceAdapter(getContext(), "tips");
        tipsAdapter.setOnPlaceClickListener(this);
        tipsAdapter.setOnPlaceSaveListener(this);
        tipsRecyclerView.setAdapter(tipsAdapter);
    }

    /**
     * Set up click listeners for UI elements
     */
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

    /**
     * Load data for all categories
     */
    private void loadData() {
        // Search nearby if we have user location
        if (userLatitude != 0 && userLongitude != 0) {
            loadNearbyData();
        } else {
            // Otherwise use a default popular location
            loadDefaultLocationData();
        }
    }

    /**
     * Load nearby places
     */
    private void loadNearbyData() {
        // Load restaurants
        placeViewModel.searchNearby(userLatitude, userLongitude, "catering.restaurant", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        restaurantsAdapter.setPlaces(resource.data);
                    }
                });

        // Load attractions
        placeViewModel.searchNearby(userLatitude, userLongitude, "tourism.attraction,tourism.sights", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        attractionsAdapter.setPlaces(resource.data);
                    }
                });

        // Load hotels
        placeViewModel.searchNearby(userLatitude, userLongitude, "accommodation.hotel", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        hotelsAdapter.setPlaces(resource.data);
                    }
                });

        // Load tips (using tourism.information category)
        placeViewModel.searchNearby(userLatitude, userLongitude, "tourism.information", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        tipsAdapter.setPlaces(resource.data);
                    }
                });
    }

    /**
     * Load data for a default location
     */
    private void loadDefaultLocationData() {
        // Load restaurants
        placeViewModel.getPlacesByLocation(DEFAULT_LOCATION_ID, "catering.restaurant", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        restaurantsAdapter.setPlaces(resource.data);
                    }
                });

        // Load attractions
        placeViewModel.getPlacesByLocation(DEFAULT_LOCATION_ID, "tourism.attraction,tourism.sights", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        attractionsAdapter.setPlaces(resource.data);
                    }
                });

        // Load hotels
        placeViewModel.getPlacesByLocation(DEFAULT_LOCATION_ID, "accommodation.hotel", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        hotelsAdapter.setPlaces(resource.data);
                    }
                });

        // Load tips (using tourism.information category)
        placeViewModel.getPlacesByLocation(DEFAULT_LOCATION_ID, "tourism.information", 10)
                .observe(getViewLifecycleOwner(), resource -> {
                    if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                        tipsAdapter.setPlaces(resource.data);
                    }
                });
    }

    /**
     * Try to get user's location if permission is granted
     */
    private void getUserLocation() {
        if (getContext() == null) return;

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // Get last known location
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                userLatitude = lastLocation.getLatitude();
                userLongitude = lastLocation.getLongitude();
            }
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
                loadData();
            }
        }
    }

    // PlaceAdapter.OnPlaceClickListener implementation

    @Override
    public void onPlaceClick(Place place) {
        // Handle place click - navigate to details
        Toast.makeText(getContext(), "Selected: " + place.getName(), Toast.LENGTH_SHORT).show();

        // Example navigation (you would need to create this destination)
        // Bundle args = new Bundle();
        // args.putString("placeId", place.getPlaceId());
        // Navigation.findNavController(getView()).navigate(R.id.action_to_place_details, args);
    }

    // PlaceAdapter.OnPlaceSaveListener implementation

    @Override
    public void onPlaceSave(Place place) {
        placeViewModel.savePlace(place);
        Toast.makeText(getContext(), place.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceUnsave(Place place) {
        placeViewModel.removePlace(place.getPlaceId());
        Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is a good place to refresh data if needed.
     */
    @Override
    public void onResume() {
        super.onResume();

        // Check saved status of places in adapters when returning to this fragment
        checkSavedStatusForPlaces(restaurantsAdapter);
        checkSavedStatusForPlaces(attractionsAdapter);
        checkSavedStatusForPlaces(hotelsAdapter);
        checkSavedStatusForPlaces(tipsAdapter);
    }

    /**
     * Update saved status for places in an adapter
     */
    private void checkSavedStatusForPlaces(PlaceAdapter adapter) {
        if (adapter == null) return;

        // Get all places from the adapter
        List<Place> places = adapter.getPlaces();
        if (places == null || places.isEmpty()) return;

        // Check saved status for each place
        for (Place place : places) {
            placeViewModel.isPlaceSaved(place.getPlaceId()).observe(getViewLifecycleOwner(), isSaved -> {
                if (place.isSaved() != isSaved) {
                    place.setSaved(isSaved);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}

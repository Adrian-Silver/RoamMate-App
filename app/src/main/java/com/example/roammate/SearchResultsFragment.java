package com.example.roammate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roammate.adapters.PlaceAdapter;
import com.example.roammate.data.model.Place;
import com.example.roammate.util.Resource;
import com.example.roammate.viewmodel.PlaceViewModel;

import java.util.List;

//public class SearchResultsFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private ProgressBar progressBar;
//    private TextView emptyView;
//    private SearchResultAdapter adapter;
//
//    private String category = "all";
//    private String currentQuery = "";
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_search_results, container, false);
//
//        // Initialize views
//        recyclerView = root.findViewById(R.id.recycler_view);
//        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
//        progressBar = root.findViewById(R.id.progress_bar);
//        emptyView = root.findViewById(R.id.empty_view);
//
//        // Setup RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new SearchResultAdapter(getContext());
//        recyclerView.setAdapter(adapter);
//
//        // Setup SwipeRefreshLayout
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            // Refresh results with the same query
//            if (!currentQuery.isEmpty()) {
//                performSearch(currentQuery);
//            }
//        });
//
//        return root;
//    }
//
//    public void performSearch(String query) {
//        if (query.isEmpty()) {
//            return;
//        }
//
//        currentQuery = query;
//
//        // Show loading state
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//        emptyView.setVisibility(View.GONE);
//
//        // Here we would typically call a repository or ViewModel method to fetch search results
//        // For now, we'll simulate a network call with a delay
//
//        // Simulating API call delay
//        getView().postDelayed(() -> {
//            // Hide loading state
//            progressBar.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
//
//            // This is where we would process the API response and update the adapter
//            // For demonstration, we'll just show the recycler view with mock data
//
//            // If we received results
//            boolean hasResults = true; // This would be based on actual API response
//
//            if (hasResults) {
//                // The actual data would come from the API
//                recyclerView.setVisibility(View.VISIBLE);
//                emptyView.setVisibility(View.GONE);
//
//                // This would use real data from the API
//                adapter.setSearchResults(getMockResults());
//            } else {
//                // No results case
//                recyclerView.setVisibility(View.GONE);
//                emptyView.setVisibility(View.VISIBLE);
//            }
//        }, 1500); // Simulate network delay
//    }
//
//    // This is just a placeholder for mock data
//    // In a real app, this data would come from the network or database
//    private SearchResult[] getMockResults() {
//        // Return different mock data depending on the category
//        return new SearchResult[]{
//                // Sample data for demonstration
//                new SearchResult("1", "Sample Place 1", category,
//                        "Sample Address 1", 4.5f, 1.2f, ""),
//                new SearchResult("2", "Sample Place 2", category,
//                        "Sample Address 2", 3.5f, 2.3f, ""),
//                new SearchResult("3", "Sample Place 3", category,
//                        "Sample Address 3", 5.0f, 0.8f, "")
//        };
//    }
//
//    // Inner class for search result model
//    public static class SearchResult {
//        private String id;
//        private String name;
//        private String category;
//        private String address;
//        private float rating;
//        private float distance;
//        private String imageUrl;
//
//        public SearchResult(String id, String name, String category, String address,
//                            float rating, float distance, String imageUrl) {
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
//        public float getDistance() { return distance; }
//        public String getImageUrl() { return imageUrl; }
//    }
//}

//public class SearchResultsFragment extends Fragment implements PlaceAdapter.OnPlaceClickListener, PlaceAdapter.OnPlaceSaveListener {
//
//    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private ProgressBar progressBar;
//    private TextView emptyView;
//    private PlaceAdapter adapter;
//
//    private PlaceViewModel placeViewModel;
//
//    private String category = "all";
//    private String currentQuery = "";
//    private String currentPlaceId = null;
//
//    // Constructor
//    public SearchResultsFragment() {
//        // Required empty constructor
//    }
//
//    /**
//     * Set the category for this fragment
//     */
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_search_results, container, false);
//
//        // Initialize views
//        recyclerView = root.findViewById(R.id.recycler_view);
//        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
//        progressBar = root.findViewById(R.id.progress_bar);
//        emptyView = root.findViewById(R.id.empty_view);
//
//        // Initialize ViewModel
//        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
//
//        // Setup RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new PlaceAdapter(getContext(), category);
//        adapter.setOnPlaceClickListener(this);
//        adapter.setOnPlaceSaveListener(this);
//        recyclerView.setAdapter(adapter);
//
//        // Setup SwipeRefreshLayout
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            // Refresh results with the same parameters
//            if (currentPlaceId != null) {
//                performSearchByPlaceId(currentPlaceId);
//            }
////            else if (!currentQuery.isEmpty()) {
////                performSearchByText(currentQuery);
////            }
//            // If there's no place ID set yet, just hide the refresh indicator
//            else {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//        // Set refresh indicator colors
//        swipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light
//        );
//
//        return root;
//    }
//
//    /**
//     * Perform search using a place ID
//     */
//    public void performSearchByPlaceId(String placeId) {
//        if (placeId == null || placeId.isEmpty()) {
//            showEmptyState("Invalid location selected");
//            return;
//        }
//
//        currentPlaceId = placeId;
////        currentQuery = "";
//
//        // Show loading state
//        showLoading();
//
//        // Determine which API categories to search based on the tab
//        String apiCategories = getCategoryParam();
//
//        // Add a timeout mechanism to handle hanging requests
//        final Handler timeoutHandler = new Handler(Looper.getMainLooper());
//        final Runnable timeoutRunnable = () -> {
//            if (progressBar.getVisibility() == View.VISIBLE) {
//                // If still loading after timeout, show error
//                hideLoading();
//                showEmptyState("Request timed out. Pull down to try again.");
//            }
//        };
//
//        // Set a 15-second timeout
//        timeoutHandler.postDelayed(timeoutRunnable, 15000);
//
//        // Call the repository method to fetch data
//        placeViewModel.getPlacesByLocation(placeId, apiCategories, 20)
////                .observe(getViewLifecycleOwner(), this::processSearchResult);
//                .observe(getViewLifecycleOwner(), resource -> {
//                    // Cancel timeout when we get a response
//                    timeoutHandler.removeCallbacks(timeoutRunnable);
//
//                    processSearchResult(resource);
//                });
//
//    }
//
//
//
////    /**
////     * Perform search using text query
////     */
////    public void performSearchByText(String query) {
////        if (query.isEmpty()) {
////            return;
////        }
////
////        currentQuery = query;
////        currentPlaceId = null;
////
////        // Show loading state
////        showLoading();
////
////        // Here we could use a text-based search API
////        // For simplicity, we'll just show a message that place ID is needed
////        // In a real app, you would implement a text search here
////
////        // Simulate search delay
////        getView().postDelayed(() -> {
////            hideLoading();
////            showEmptyState("Please select a location from the suggestions");
////            Toast.makeText(getContext(), "Select a location for better results", Toast.LENGTH_SHORT).show();
////        }, 1000);
////    }
//
////    /**
////     * Process API response
////     */
////    private void processSearchResult(Resource<List<Place>> resource) {
////        hideLoading();
////
////        switch (resource.status) {
////            case SUCCESS:
////                if (resource.data != null && !resource.data.isEmpty()) {
////                    adapter.setPlaces(resource.data);
////                    showResults();
////                } else {
////                    showEmptyState("No places found");
////                }
////                break;
////
////            case ERROR:
////                showEmptyState("Error: " + resource.message);
////                Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_SHORT).show();
////                break;
////
////            case LOADING:
////                showLoading();
////                break;
////        }
////    }
//
//    /**
//            * Process API response
//     */
//    private void processSearchResult(Resource<List<Place>> resource) {
//        hideLoading();
//
//        switch (resource.status) {
//            case SUCCESS:
//                if (resource.data != null && !resource.data.isEmpty()) {
//                    adapter.setPlaces(resource.data);
//                    showResults();
//                } else {
//                    showEmptyState("No places found in this category");
//                }
//                break;
//
//            case ERROR:
//                showEmptyState("Error: " + resource.message);
//                Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_SHORT).show();
//                Log.e("SearchResults", "Search error: " + resource.message);
//                break;
//
//            case LOADING:
//                showLoading();
//                break;
//        }
//    }
//
//    /**
//     * Get API category parameter based on the selected tab
//     */
//    private String getCategoryParam() {
//        switch (category) {
//            case "attractions":
//                return "tourism.attraction,tourism.sights";
//            case "hotels":
//                return "accommodation.hotel,accommodation";
//            case "restaurants":
//                return "catering.restaurant,catering";
//            default: // "all"
//                return "tourism.attraction,tourism.sights,accommodation.hotel,accommodation,catering.restaurant,catering";
//        }
//    }
//
//    // UI state methods
//
//    private void showLoading() {
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//        emptyView.setVisibility(View.GONE);
////        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    private void hideLoading() {
//        progressBar.setVisibility(View.GONE);
//        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    private void showResults() {
//        recyclerView.setVisibility(View.VISIBLE);
//        emptyView.setVisibility(View.GONE);
//    }
//
//    private void showEmptyState(String message) {
//        recyclerView.setVisibility(View.GONE);
//        emptyView.setVisibility(View.VISIBLE);
//        emptyView.setText(message);
//        // Make sure the refresh indicator is also hidden
//        swipeRefreshLayout.setRefreshing(false);
//
//    }
//
//    // PlaceAdapter.OnPlaceClickListener implementation
//
//    @Override
//    public void onPlaceClick(Place place) {
//        // Navigate to place details
//        // In a real app, you would create a details screen and pass the place ID
//        Toast.makeText(getContext(), "Selected: " + place.getName(), Toast.LENGTH_SHORT).show();
//
//        // Example navigation (you would need to create this destination)
//        // Bundle args = new Bundle();
//        // args.putString("placeId", place.getPlaceId());
//        // Navigation.findNavController(getView()).navigate(R.id.action_to_place_details, args);
//
//        // Navigate to place details
//        Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
//        intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
//        startActivity(intent);
//    }
//
//    // PlaceAdapter.OnPlaceSaveListener implementation
//
//    @Override
//    public void onPlaceSave(Place place) {
//        placeViewModel.savePlace(place);
//        Toast.makeText(getContext(), place.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPlaceUnsave(Place place) {
//        placeViewModel.removePlace(place.getPlaceId());
//        Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
//    }
//}

public class SearchResultsFragment extends Fragment implements PlaceAdapter.OnPlaceClickListener, PlaceAdapter.OnPlaceSaveListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyView;
    private PlaceAdapter adapter;
    private View rootView;

    private PlaceViewModel placeViewModel;

    private String category = "all";
    private String currentPlaceId = null;
    private boolean isViewInitialized = false;

    // Constructor
    public SearchResultsFragment() {
        // Required empty constructor
    }

    /**
     * Set the category for this fragment
     */
    public void setCategory(String category) {
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Initialize view model
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);

        // This ensures we don't try to use views before they're ready
        isViewInitialized = false;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup SwipeRefreshLayout
        setupSwipeRefresh();

        // Mark views as initialized
        isViewInitialized = true;

        // If we already have a place ID, search again
        if (currentPlaceId != null && !currentPlaceId.isEmpty()) {
            performSearchByPlaceId(currentPlaceId);
        }
    }

    /**
     * Initialize views
     */
    private void initializeViews() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        progressBar = rootView.findViewById(R.id.progress_bar);
        emptyView = rootView.findViewById(R.id.empty_view);
    }

    /**
     * Setup RecyclerView
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlaceAdapter(getContext(), category);
        adapter.setOnPlaceClickListener(this);
        adapter.setOnPlaceSaveListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Setup SwipeRefreshLayout
     */
    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Refresh results with the same parameters
            if (currentPlaceId != null) {
                performSearchByPlaceId(currentPlaceId);
            } else {
                // If there's no place ID set yet, just hide the refresh indicator
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Set refresh indicator colors
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    /**
     * Perform search using a place ID
     */
    public void performSearchByPlaceId(String placeId) {
        if (placeId == null || placeId.isEmpty()) {
            Log.e("SearchResults", "Invalid place ID provided");
            showEmptyStateIfPossible("Invalid location selected");
            return;
        }

        // Save the place ID even if views aren't initialized yet
        currentPlaceId = placeId;

        // If views aren't initialized yet, we'll search when they are
        if (!isViewInitialized || !isAdded()) {
            Log.d("SearchResults", "Views not initialized yet, will search later");
            return;
        }

        // Show loading state
        showLoading();

        // Determine which API categories to search based on the tab
        String apiCategories = getCategoryParam();

        // Add a timeout mechanism to handle hanging requests
        final Handler timeoutHandler = new Handler(Looper.getMainLooper());
        final Runnable timeoutRunnable = () -> {
            if (isAdded() && isViewInitialized && progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
                // If still loading after timeout, show error
                hideLoading();
                showEmptyStateIfPossible("Request timed out. Pull down to try again.");
            }
        };

        // Set a 15-second timeout
        timeoutHandler.postDelayed(timeoutRunnable, 15000);

        // Call the repository method to fetch data
        placeViewModel.searchPOIsInPlace(placeId, apiCategories, 20)
                .observe(getViewLifecycleOwner(), resource -> {
                    // Cancel timeout when we get a response
                    timeoutHandler.removeCallbacks(timeoutRunnable);

                    if (isAdded() && isViewInitialized) {
                        processSearchResult(resource);
                    }
                });
    }

    /**
     * Process API response
     */
    private void processSearchResult(Resource<List<Place>> resource) {
        if (!isAdded() || !isViewInitialized) {
            return;
        }

        hideLoading();

        switch (resource.status) {
            case SUCCESS:
                if (resource.data != null && !resource.data.isEmpty()) {
                    adapter.setPlaces(resource.data);
                    showResults();
                } else {
                    // Explicitly handle empty results
                    showEmptyStateIfPossible("No places found in this category");
                }
                break;

            case ERROR:
                showEmptyStateIfPossible("Error: " + resource.message);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_SHORT).show();
                }
                Log.e("SearchResults", "Search error: " + resource.message);
                break;

            case LOADING:
                showLoading();
                break;
        }
    }

    /**
     * Get API category parameter based on the selected tab
     */
    private String getCategoryParam() {
        switch (category) {
            case "attractions":
                return "tourism.attraction,tourism.sights";
            case "hotels":
                return "accommodation.hotel,accommodation";
            case "restaurants":
                return "catering.restaurant,catering";
            default: // "all"
                return "tourism.attraction,tourism.sights,accommodation.hotel,accommodation,catering.restaurant,catering";
        }
    }

    // UI state methods with null checks
    private void showLoading() {
        if (isAdded() && isViewInitialized) {
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (emptyView != null) emptyView.setVisibility(View.GONE);
        }
    }

    private void hideLoading() {
        if (isAdded() && isViewInitialized) {
            if (progressBar != null) progressBar.setVisibility(View.GONE);
            if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showResults() {
        if (isAdded() && isViewInitialized) {
            if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
            if (emptyView != null) emptyView.setVisibility(View.GONE);
        }
    }

    private void showEmptyStateIfPossible(String message) {
        if (isAdded() && isViewInitialized) {
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (emptyView != null) {
                emptyView.setVisibility(View.VISIBLE);
                emptyView.setText(message);
            }
            // Make sure the refresh indicator is also hidden
            if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
        }
    }

    // PlaceAdapter.OnPlaceClickListener implementation
    @Override
    public void onPlaceClick(Place place) {
        if (!isAdded()) return;

        // Navigate to place details
        Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_ID, place.getPlaceId());
        startActivity(intent);
    }

    // PlaceAdapter.OnPlaceSaveListener implementation
    @Override
    public void onPlaceSave(Place place) {
        if (!isAdded() || placeViewModel == null) return;

        placeViewModel.savePlace(place);
        if (getContext() != null) {
            Toast.makeText(getContext(), place.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPlaceUnsave(Place place) {
        if (!isAdded() || placeViewModel == null) return;

        placeViewModel.removePlace(place.getPlaceId());
        if (getContext() != null) {
            Toast.makeText(getContext(), place.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}


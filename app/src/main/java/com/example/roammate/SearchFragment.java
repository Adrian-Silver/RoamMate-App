package com.example.roammate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.roammate.adapters.PlaceNameAdapter;
import com.example.roammate.adapters.SearchPagerAdapter;
import com.example.roammate.data.model.Place;
import com.example.roammate.util.Resource;
import com.example.roammate.viewmodel.PlaceViewModel;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


//public class SearchFragment extends Fragment {
//
//    private Toolbar toolbar;
////    private EditText searchEditText;
//    private SearchView searchView;
//    private TabLayout tabLayout;
//    private ViewPager2 viewPager;
//    private SearchPagerAdapter searchPagerAdapter;
//
//    private String searchQuery = "";
//    private String initialCategory = null;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_search, container, false);
//
//        // Get arguments (if any)
//        if (getArguments() != null && getArguments().containsKey("category")) {
//            initialCategory = getArguments().getString("category");
//        }
//
//        // Initialize views
//        toolbar = root.findViewById(R.id.toolbar);
//        searchView = root.findViewById(R.id.search_edit_text);
//        tabLayout = root.findViewById(R.id.tab_layout);
//        viewPager = root.findViewById(R.id.view_pager);
//
//        // Set up toolbar
//        toolbar.setNavigationOnClickListener(v -> {
//            Navigation.findNavController(v).navigateUp();
//        });
//
//        // Set up search SearchView
//        setupSearchView();
//
//        // Set up ViewPager with TabLayout
//        setupViewPagerWithTabs();
//
//        return root;
//    }
//
//    private void setupSearchView() {
//        // Configuration
//        searchView.setIconifiedByDefault(false);
//        searchView.setSubmitButtonEnabled(true);
//        searchView.requestFocus();
//
//        // Query Listener
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                performSearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // TODO: Optionally implement real-time search suggestions
//                return false;
//            }
//        });
//
//    }
//
//    private void setupViewPagerWithTabs() {
//        // Create and set adapter for ViewPager
//        searchPagerAdapter = new SearchPagerAdapter(this);
//        viewPager.setAdapter(searchPagerAdapter);
//
//        // Connect TabLayout with ViewPager2
//        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
//            switch (position) {
//                case 0:
//                    tab.setText("All");
//                    break;
//                case 1:
//                    tab.setText("Attractions");
//                    break;
//                case 2:
//                    tab.setText("Hotels");
//                    break;
//                case 3:
//                    tab.setText("Restaurants");
//                    break;
//            }
//        }).attach();
//
//        // Select initial tab based on category argument
//        if (initialCategory != null) {
//            int tabPosition = 0;
//            switch (initialCategory) {
//                case "attractions":
//                    tabPosition = 1;
//                    break;
//                case "hotels":
//                    tabPosition = 2;
//                    break;
//                case "restaurants":
//                    tabPosition = 3;
//                    break;
//            }
//            viewPager.setCurrentItem(tabPosition, false);
//        }
//    }
//
//    private void performSearch(String query) {
//        if (query.isEmpty()) {
//            return;
//        }
//
//        searchQuery = query;
//
//        // Pass search query to all tabs
//        for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
////            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + i);
//            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
//            if (fragment instanceof SearchResultsFragment) {
//                ((SearchResultsFragment) fragment).performSearch(query);
//            }
//        }
//    }
//}

//public class SearchFragment extends Fragment {
//
//    private Toolbar toolbar;
//    private SearchView searchView;
//    private TabLayout tabLayout;
//    private ViewPager2 viewPager;
//    private SearchPagerAdapter searchPagerAdapter;
//
//    private PlaceViewModel placeViewModel;
//
//    private String searchQuery = "";
//    private String selectedPlaceId = null;
//    private String initialCategory = null;
//
//    // For autocomplete
//    private List<Place> suggestedPlaces = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_search, container, false);
//
//        // Get arguments (if any)
//        if (getArguments() != null && getArguments().containsKey("category")) {
//            initialCategory = getArguments().getString("category");
//        }
//
//        // Initialize views
//        toolbar = root.findViewById(R.id.toolbar);
//        searchView = root.findViewById(R.id.search_edit_text);
//        tabLayout = root.findViewById(R.id.tab_layout);
//        viewPager = root.findViewById(R.id.view_pager);
//
//        // Initialize view model
//        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
//
//        // Set up toolbar
//        toolbar.setNavigationOnClickListener(v -> {
//            Navigation.findNavController(v).navigateUp();
//        });
//
//        // Set up search view
//        setupSearchView();
//
//        // Set up ViewPager with TabLayout
//        setupViewPagerWithTabs();
//
//        return root;
//    }
//
//    /**
//     * Set up the SearchView with query listeners
//     */
//    private void setupSearchView() {
//        // Configure the SearchView
//        searchView.setIconifiedByDefault(false);
//        searchView.setSubmitButtonEnabled(true);
//        searchView.requestFocus();
//
//        // Set up query listener
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (TextUtils.isEmpty(query)) {
//                    return false;
//                }
//
//                if (selectedPlaceId != null) {
//                    // If we have a selected place ID, use that
//                    performSearch(selectedPlaceId);
//                } else {
//                    // Otherwise just search for the query text
//                    // This might not be as accurate without a place ID
//                    searchByText(query);
//                }
//
//                // Clear focus and hide keyboard
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.length() > 2) {
//                    // Get suggestions for the text
//                    placeViewModel.getPlaceSuggestions(newText).observe(getViewLifecycleOwner(), resource -> {
//                        if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
//                            suggestedPlaces = resource.data;
//
//                            // Since we don't have a dropdown, just show the first suggestion as a toast
//                            // In a real app, you'd implement a proper dropdown UI
//                            if (!suggestedPlaces.isEmpty()) {
//                                Place firstSuggestion = suggestedPlaces.get(0);
//                                selectedPlaceId = firstSuggestion.getPlaceId();
//                                Toast.makeText(requireContext(),
//                                        "Found: " + firstSuggestion.getName(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//                return true;
//            }
//        });
//    }
//
////    /**
////     * Show autocomplete suggestions
////     */
////    private void showSuggestions(List<Place> suggestions) {
////        // This is a simplified approach. In a real app, you might use a custom dropdown
////
////        // Create suggestion strings
////        ArrayList<String> suggestionStrings = new ArrayList<>();
////        for (Place place : suggestions) {
////            suggestionStrings.add(place.getName() + ", " + place.getCity() + ", " + place.getCountry());
////        }
////
////        // Create and set adapter
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(
////                requireContext(),
////                android.R.layout.simple_dropdown_item_1line,
////                suggestionStrings);
////
////        // Here you need to implement a dropdown mechanism
////        // For simplicity, this example assumes you have an AutoCompleteTextView in your layout
////        // You would need to adjust this based on your actual UI
////        if (searchView instanceof AutoCompleteTextView) {
////            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) searchView;
////            autoCompleteTextView.setAdapter(adapter);
////            autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
////                if (position < suggestedPlaces.size()) {
////                    // User selected a suggestion
////                    Place selectedPlace = suggestedPlaces.get(position);
////                    selectedPlaceId = selectedPlace.getPlaceId();
////                    searchView.setQuery(selectedPlace.getName(), false);
////                }
////            });
////
////            if (!suggestionStrings.isEmpty()) {
////                autoCompleteTextView.showDropDown();
////            }
////        } else {
////            // If using regular SearchView, you'd need a custom solution
////            // This is just a fallback example showing the first suggestion
////            if (!suggestions.isEmpty()) {
////                selectedPlaceId = suggestions.get(0).getPlaceId();
////                Toast.makeText(requireContext(),
////                        "Found: " + suggestions.get(0).getName(),
////                        Toast.LENGTH_SHORT).show();
////            }
////        }
////    }
//
//    /**
//     * Set up ViewPager with TabLayout
//     */
//    private void setupViewPagerWithTabs() {
//        // Create and set adapter for ViewPager
//        searchPagerAdapter = new SearchPagerAdapter(this);
//        viewPager.setAdapter(searchPagerAdapter);
//
//        // Connect TabLayout with ViewPager2
//        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
//            switch (position) {
//                case 0:
//                    tab.setText("All");
//                    break;
//                case 1:
//                    tab.setText("Attractions");
//                    break;
//                case 2:
//                    tab.setText("Hotels");
//                    break;
//                case 3:
//                    tab.setText("Restaurants");
//                    break;
//            }
//        }).attach();
//
//        // Select initial tab based on category argument
//        if (initialCategory != null) {
//            int tabPosition = 0;
//            switch (initialCategory) {
//                case "attractions":
//                    tabPosition = 1;
//                    break;
//                case "hotels":
//                    tabPosition = 2;
//                    break;
//                case "restaurants":
//                    tabPosition = 3;
//                    break;
//            }
//            viewPager.setCurrentItem(tabPosition, false);
//        }
//    }
//
//    /**
//     * Perform search using a place ID
//     */
//    private void performSearch(String placeId) {
//        searchQuery = searchView.getQuery().toString();
//
//        // Pass search parameters to all tabs
//        for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
//            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
//            if (fragment instanceof SearchResultsFragment) {
//                ((SearchResultsFragment) fragment).performSearchByPlaceId(placeId);
//            }
//        }
//    }
//
//    /**
//     * Perform search using text query
//     */
//    private void searchByText(String query) {
//        searchQuery = query;
//
//        // Notify all tabs to search using the text
//        for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
//            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
//            if (fragment instanceof SearchResultsFragment) {
//                ((SearchResultsFragment) fragment).performSearchByText(query);
//            }
//        }
//    }
//}

public class SearchFragment extends Fragment implements PlaceNameAdapter.OnPlaceClickListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private SearchPagerAdapter searchPagerAdapter;
    private LinearProgressIndicator progressIndicator;
    private RecyclerView placeNamesRecyclerView;
    private PlaceNameAdapter placeNameAdapter;

    private PlaceViewModel placeViewModel;

    private String searchQuery = "";
    private String initialCategory = null;
    private boolean isSearchingPlaces = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        // Get arguments (if any)
        if (getArguments() != null && getArguments().containsKey("category")) {
            initialCategory = getArguments().getString("category");
        }

        // Initialize views
        toolbar = root.findViewById(R.id.toolbar);
        searchView = root.findViewById(R.id.search_edit_text);
        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager = root.findViewById(R.id.view_pager);
        progressIndicator = root.findViewById(R.id.progress_indicator);
        placeNamesRecyclerView = root.findViewById(R.id.place_names_recycler_view);

        // Initialize view model
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);

        // Set up toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // If showing search results for places, go back to place search
            if (isSearchingPlaces) {
                showPlaceNameSearch();
            } else {
                // Otherwise, navigate up
                getParentFragmentManager().popBackStack();
            }
        });

        // Set up search view
        setupSearchView();

        // Set up RecyclerView for place names
        setupPlaceNamesRecyclerView();

        // Set up ViewPager with TabLayout (initially hidden)
        setupViewPagerWithTabs();

        // Initial state - show place name search
        showPlaceNameSearch();

        return root;
    }

    /**
     * Set up the SearchView with query listeners
     */
    private void setupSearchView() {
        // Configure the SearchView
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.requestFocus();

        // Set up query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return false;
                }

                if (!isSearchingPlaces) {
                    // If we're searching for place names
                    searchPlacesByName(query);
                } else {
                    // If we're already searching POIs within a place, ignore
                    Toast.makeText(requireContext(), "Select a different location to search again", Toast.LENGTH_SHORT).show();
                }

                // Clear focus and hide keyboard
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // We can implement live suggestions here if needed
                return true;
            }
        });
    }

    /**
     * Set up RecyclerView for displaying place name search results
     */
    private void setupPlaceNamesRecyclerView() {
        placeNameAdapter = new PlaceNameAdapter(requireContext(), this);
        placeNamesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        placeNamesRecyclerView.setAdapter(placeNameAdapter);
    }

    /**
     * Set up ViewPager with TabLayout
     */
    private void setupViewPagerWithTabs() {
        // Create and set adapter for ViewPager
        searchPagerAdapter = new SearchPagerAdapter(this);
        viewPager.setAdapter(searchPagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("All");
                    break;
                case 1:
                    tab.setText("Attractions");
                    break;
                case 2:
                    tab.setText("Hotels");
                    break;
                case 3:
                    tab.setText("Restaurants");
                    break;
            }
        }).attach();

        // Select initial tab based on category argument
        if (initialCategory != null) {
            int tabPosition = 0;
            switch (initialCategory) {
                case "attractions":
                    tabPosition = 1;
                    break;
                case "hotels":
                    tabPosition = 2;
                    break;
                case "restaurants":
                    tabPosition = 3;
                    break;
            }
            viewPager.setCurrentItem(tabPosition, false);
        }
    }

    /**
     * Show place name search UI
     */
    private void showPlaceNameSearch() {
        isSearchingPlaces = false;
        placeNamesRecyclerView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        searchView.setQueryHint("Search for a place...");
        toolbar.setTitle("Search Places");
    }

    /**
     * Show POI search results UI
     */
    private void showPOISearchResults(String placeName) {
        isSearchingPlaces = true;
        placeNamesRecyclerView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        searchView.setQueryHint("Searching in " + placeName);
        searchView.setQuery("", false);
        toolbar.setTitle("Places in " + placeName);
    }

    /**
     * Search for places by name
     */
    private void searchPlacesByName(String query) {
        searchQuery = query;

        // Show loading indicator
        progressIndicator.setVisibility(View.VISIBLE);

        // Clear previous results
        placeNameAdapter.setPlaces(new ArrayList<>());

        // Search for places by name
        placeViewModel.searchPlacesByName(query, 10).observe(getViewLifecycleOwner(), resource -> {
            progressIndicator.setVisibility(View.GONE);

            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                if (resource.data.isEmpty()) {
                    Toast.makeText(getContext(), "No places found with that name", Toast.LENGTH_SHORT).show();
                } else {
                    placeNameAdapter.setPlaces(resource.data);
                }
            } else if (resource.status == Resource.Status.ERROR) {
//                Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_SHORT).show();

                // Show detailed error message
                String errorMsg = "Search failed";
                if (resource.message != null) {
                    errorMsg = resource.message;
                    // Log the full error for debugging
                    Log.e("SearchFragment", "Search error: " + errorMsg);
                }

                // Show a user-friendly message
                Toast.makeText(getContext(),
                        "Couldn't find places matching \"" + query + "\"",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Search for POIs within a selected place
     */
//    private void searchPOIsInPlace(Place place) {
//        // Switch to POI search UI
//        showPOISearchResults(place.getName());
//
//        // Get all the tabs and send the place ID to each
//        String placeId = place.getPlaceId();
//
//        // Notify all tabs to search using the place ID
//        for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
////            SearchResultsFragment fragment = getChildFragmentManager().findFragmentByTag("f" + i);
////            if (fragment instanceof SearchResultsFragment) {
////                ((SearchResultsFragment) fragment).performSearchByPlaceId(placeId);
////            }
//            SearchResultsFragment fragment = searchPagerAdapter.getFragment(i);
//            if (fragment != null) {
//                fragment.performSearchByPlaceId(placeId);
//            }
//        }
//    }
    private void searchPOIsInPlace(Place place) {
        if (place == null || place.getPlaceId() == null) {
            Toast.makeText(getContext(), "Invalid place selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get place ID
        String placeId = place.getPlaceId();

        // Switch to POI search UI
        showPOISearchResults(place.getName());

        // Log for debugging
        Log.d("SearchFragment", "Searching POIs for place: " + place.getName() + " (ID: " + placeId + ")");

        // Use a slight delay to ensure the ViewPager has completely initialized its fragments
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Notify all tabs to search using the place ID
            for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
                SearchResultsFragment fragment = searchPagerAdapter.getFragment(i);
                if (fragment != null) {
                    fragment.performSearchByPlaceId(placeId);
                } else {
                    Log.e("SearchFragment", "Fragment at position " + i + " is null");
                }
            }
        }, 100); // Short delay to let the ViewPager initialize
    }


    // PlaceNameAdapter.OnPlaceClickListener implementation
    @Override
    public void onPlaceClick(Place place) {
        // When a place name is selected, search for POIs within that place
        searchPOIsInPlace(place);
    }
}

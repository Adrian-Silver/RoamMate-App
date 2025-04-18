package com.example.roammate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roammate.data.model.Feature;
import com.example.roammate.data.model.Place;
import com.example.roammate.data.model.Properties;
import com.example.roammate.util.CategoryImageProvider;
import com.example.roammate.util.Resource;
import com.example.roammate.viewmodel.PlaceViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

/**
 * Fragment for displaying place details
 */
public class PlaceDetailsFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private String placeId;
    private PlaceViewModel placeViewModel;

    // Views
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView placeImage;
    private TextView placeName;
    private TextView placeCategory;
    private RatingBar placeRating;
    private TextView placeAddress;
    private TextView placeWebsite;
    private TextView placeHours;
    private LinearLayout websiteContainer;
    private LinearLayout hoursContainer;

    // Location info
    private TextView placeCountry;
    private TextView placeState;
    private TextView placeCity;
    private TextView placeCounty;
    private TableRow countyRow;

    // Category specific
    private TextView categoryTitle;
    private LinearLayout categoryDetailsContainer;

    // Action buttons
    private Button btnSave;
    private Button btnDirections;
    private FloatingActionButton fabShare;

    private boolean isPlaceSaved = false;
    private Place currentPlace;

    public PlaceDetailsFragment() {
        // Required empty constructor
    }

    /**
     * Create a new instance of PlaceDetailsFragment
     */
    public static PlaceDetailsFragment newInstance(String placeId) {
        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_ID, placeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeId = getArguments().getString(ARG_PLACE_ID);
        }

        // Initialize ViewModel
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);

        // Initialize views
        initializeViews(view);

        // Set up toolbar
        setupToolbar();

        // Set up action buttons
        setupActionButtons();

        // Load place details
        loadPlaceDetails();

        return view;
    }

    /**
     * Initialize all views from the layout
     */
    private void initializeViews(View view) {
        // Toolbar and header
        toolbar = view.findViewById(R.id.toolbar);
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        placeImage = view.findViewById(R.id.place_image);

        // Basic info
        placeName = view.findViewById(R.id.place_name);
        placeCategory = view.findViewById(R.id.place_category);
        placeRating = view.findViewById(R.id.place_rating);
        placeAddress = view.findViewById(R.id.place_address);
        placeWebsite = view.findViewById(R.id.place_website);
        placeHours = view.findViewById(R.id.place_hours);
        websiteContainer = view.findViewById(R.id.website_container);
        hoursContainer = view.findViewById(R.id.hours_container);

        // Location info
        placeCountry = view.findViewById(R.id.place_country);
        placeState = view.findViewById(R.id.place_state);
        placeCity = view.findViewById(R.id.place_city);
        placeCounty = view.findViewById(R.id.place_county);
        countyRow = view.findViewById(R.id.county_row);

        // Category specific
        categoryTitle = view.findViewById(R.id.category_title);
        categoryDetailsContainer = view.findViewById(R.id.category_details_container);

        // Action buttons
        btnSave = view.findViewById(R.id.btn_save);
        btnDirections = view.findViewById(R.id.btn_directions);
        fabShare = view.findViewById(R.id.fab_share);
    }

    /**
     * Set up toolbar with navigation
     */
    private void setupToolbar() {
        if (getActivity() instanceof PlaceDetailsActivity) {
            ((PlaceDetailsActivity) getActivity()).setSupportActionBar(toolbar);
            ((PlaceDetailsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Handle navigation click
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }
    }

    /**
     * Set up action buttons
     */
    private void setupActionButtons() {
        // Save button
        btnSave.setOnClickListener(v -> toggleSavePlace());

        // Directions button
        btnDirections.setOnClickListener(v -> openDirections());

        // Share FAB
        fabShare.setOnClickListener(v -> sharePlace());
    }

    /**
     * Load place details from ViewModel
     */
    private void loadPlaceDetails() {
        placeViewModel.getPlaceDetails(placeId).observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                currentPlace = resource.data;
                displayPlaceDetails(resource.data);
                checkIfPlaceIsSaved();
            } else if (resource.status == Resource.Status.ERROR) {
                Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Display place details in the UI
     */
    private void displayPlaceDetails(Place place) {
        // Set top section
        collapsingToolbar.setTitle(place.getName());

        // Load header image
        int imageResId = CategoryImageProvider.getConsistentImageForPlace(place.getPlaceId(), place.getCategory());
        Glide.with(this)
                .load(imageResId)
                .into(placeImage);

        // Set basic info
        placeName.setText(place.getName());
        placeCategory.setText(place.getCategoryDisplayName());
        placeRating.setRating(place.getRating());
        placeAddress.setText(place.getFormattedAddress());

        // Set website if available
        if (place.getWebsite() != null && !place.getWebsite().isEmpty()) {
            placeWebsite.setText(place.getWebsite());
            websiteContainer.setVisibility(View.VISIBLE);
        } else {
            websiteContainer.setVisibility(View.GONE);
        }

        // Set opening hours if available
        if (place.getOpeningHours() != null && !place.getOpeningHours().isEmpty()) {
            placeHours.setText(place.getOpeningHours());
            hoursContainer.setVisibility(View.VISIBLE);
        } else {
            hoursContainer.setVisibility(View.GONE);
        }

        // Set location info
        placeCountry.setText(place.getCountry() != null ? place.getCountry() : "N/A");
        placeState.setText(place.getCity() != null ? place.getCity() : "N/A");
        placeCity.setText(place.getCity() != null ? place.getCity() : "N/A");

        if (place.getCountry() != null && !place.getCountry().isEmpty()) {
            placeCounty.setText(place.getCountry());
            countyRow.setVisibility(View.VISIBLE);
        } else {
            countyRow.setVisibility(View.GONE);
        }

        // Set category-specific details
        categoryDetailsContainer.removeAllViews();
        String category = place.getCategory().toLowerCase();

        if (category.contains("hotel") || category.contains("accommodation")) {
            categoryTitle.setText("Hotel Information");
            setupHotelDetails(place);
        } else if (category.contains("restaurant") || category.contains("catering")) {
            categoryTitle.setText("Restaurant Information");
            setupRestaurantDetails(place);
        } else if (category.contains("attraction") || category.contains("tourism")) {
            categoryTitle.setText("Attraction Information");
            setupAttractionDetails(place);
        } else {
            categoryTitle.setText("Details");
            setupGenericDetails(place);
        }
    }

    /**
     * Set up hotel-specific details
     */
    private void setupHotelDetails(Place place) {
        // Stars
        if (place.getStars() != null) {
            addDetailRow("Stars", getStarsString(place.getStars()));
        }

        // Internet access
        if (place.getInternetAccess() != null) {
            String internetInfo = "Unknown";
            if ("wlan".equals(place.getInternetAccess()) || "yes".equals(place.getInternetAccess())) {
                internetInfo = "Wi-Fi available";
            } else if ("no".equals(place.getInternetAccess())) {
                internetInfo = "No internet access";
            } else {
                internetInfo = "Internet access: " + place.getInternetAccess();
            }
            addDetailRow("Internet", internetInfo);
        }

        // Payment options
        if (place.getPaymentOptions() != null && !place.getPaymentOptions().isEmpty()) {
            StringBuilder paymentText = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Boolean> entry : place.getPaymentOptions().entrySet()) {
                if (entry.getValue()) {
                    if (!first) {
                        paymentText.append(", ");
                    }
                    paymentText.append(entry.getKey().replace("_", " "));
                    first = false;
                }
            }
            if (paymentText.length() > 0) {
                addDetailRow("Payment", paymentText.toString());
            }
        }

        // Add any other hotel-specific fields
        if (place.getRaw() != null) {
            for (Map.Entry<String, Object> entry : place.getRaw().entrySet()) {
                if (isRelevantHotelField(entry.getKey())) {
                    addDetailRow(formatFieldName(entry.getKey()), entry.getValue().toString());
                }
            }
        }
    }

    /**
     * Set up restaurant-specific details
     */
    private void setupRestaurantDetails(Place place) {
        // Cuisine
        if (place.getCuisine() != null && !place.getCuisine().isEmpty()) {
            String cuisine = place.getCuisine().replace(";", ", ").replace("_", " ");
            // Capitalize each word
            String[] words = cuisine.split("\\s+");
            StringBuilder formattedCuisine = new StringBuilder();
            for (String word : words) {
                if (word.length() > 0) {
                    formattedCuisine.append(word.substring(0, 1).toUpperCase())
                            .append(word.substring(1)).append(" ");
                }
            }
            addDetailRow("Cuisine", formattedCuisine.toString().trim());
        }

        // Add any other restaurant-specific fields
        if (place.getRaw() != null) {
            for (Map.Entry<String, Object> entry : place.getRaw().entrySet()) {
                if (isRelevantRestaurantField(entry.getKey())) {
                    addDetailRow(formatFieldName(entry.getKey()), entry.getValue().toString());
                }
            }
        }
    }

    /**
     * Set up attraction-specific details
     */
    private void setupAttractionDetails(Place place) {
        // Fee information
        if (place.getFee() != null) {
            String feeInfo = "Unknown";
            if ("yes".equals(place.getFee())) {
                feeInfo = "Entrance fee required";
            } else if ("no".equals(place.getFee())) {
                feeInfo = "Free entrance";
            } else {
                feeInfo = place.getFee();
            }
            addDetailRow("Entrance Fee", feeInfo);
        }

        // Historic type
        if (place.getHistoric() != null) {
            String historic = place.getHistoric().replace("_", " ");
            // Capitalize first letter
            historic = historic.substring(0, 1).toUpperCase() + historic.substring(1);
            addDetailRow("Historic Type", historic);
        }

        // Leisure type
        if (place.getLeisure() != null) {
            String leisure = place.getLeisure().replace("_", " ");
            // Capitalize first letter
            leisure = leisure.substring(0, 1).toUpperCase() + leisure.substring(1);
            addDetailRow("Leisure Type", leisure);
        }

        // Add any other attraction-specific fields
        if (place.getRaw() != null) {
            for (Map.Entry<String, Object> entry : place.getRaw().entrySet()) {
                if (isRelevantAttractionField(entry.getKey())) {
                    addDetailRow(formatFieldName(entry.getKey()), entry.getValue().toString());
                }
            }
        }
    }

    /**
     * Set up generic details for any place type
     */
    private void setupGenericDetails(Place place) {
        // Add any generic fields from raw data
        if (place.getRaw() != null) {
            for (Map.Entry<String, Object> entry : place.getRaw().entrySet()) {
                if (isGenericRelevantField(entry.getKey())) {
                    addDetailRow(formatFieldName(entry.getKey()), entry.getValue().toString());
                }
            }
        }
    }

    /**
     * Add a detail row to the category details container
     */
    private void addDetailRow(String label, String value) {
        View row = getLayoutInflater().inflate(R.layout.item_detail_row, categoryDetailsContainer, false);
        TextView labelView = row.findViewById(R.id.detail_label);
        TextView valueView = row.findViewById(R.id.detail_value);

        labelView.setText(label);
        valueView.setText(value);

        categoryDetailsContainer.addView(row);
    }

    /**
     * Format a field name for display
     */
    private String formatFieldName(String fieldName) {
        // Replace underscores with spaces
        String formatted = fieldName.replace("_", " ");

        // Capitalize first letter of each word
        String[] words = formatted.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1)).append(" ");
            }
        }

        return result.toString().trim();
    }

    /**
     * Generate a stars string (e.g., "★★★☆☆")
     */
    private String getStarsString(int stars) {
        StringBuilder starsText = new StringBuilder();
        for (int i = 0; i < stars; i++) {
            starsText.append("★");
        }
        for (int i = stars; i < 5; i++) {
            starsText.append("☆");
        }
        starsText.append(" (").append(stars).append(" stars)");
        return starsText.toString();
    }

    /**
     * Check if a field is relevant for hotels
     */
    private boolean isRelevantHotelField(String fieldName) {
        String[] relevantFields = {"rooms", "beds", "stars", "accommodation", "swimming_pool", "breakfast",
                "air_conditioning", "parking", "wheelchair", "smoking"};
        for (String field : relevantFields) {
            if (fieldName.contains(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a field is relevant for restaurants
     */
    private boolean isRelevantRestaurantField(String fieldName) {
        String[] relevantFields = {"cuisine", "diet", "takeaway", "delivery", "outdoor_seating", "alcohol",
                "reservation", "smoking", "wheelchair"};
        for (String field : relevantFields) {
            if (fieldName.contains(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a field is relevant for attractions
     */
    private boolean isRelevantAttractionField(String fieldName) {
        String[] relevantFields = {"fee", "historic", "leisure", "tourism", "attraction", "museum",
                "artwork", "information", "wheelchair", "capacity"};
        for (String field : relevantFields) {
            if (fieldName.contains(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a field is generically relevant
     */
    private boolean isGenericRelevantField(String fieldName) {
        // Exclude some common non-relevant fields
        String[] nonRelevantFields = {"osm_", "name:", "housenumber", "placement", "layer", "opening_hours",
                "wikipedia", "wikidata", "source", "addr:"};
        for (String field : nonRelevantFields) {
            if (fieldName.contains(field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the place is saved and update UI accordingly
     */
    private void checkIfPlaceIsSaved() {
        placeViewModel.isPlaceSaved(placeId).observe(getViewLifecycleOwner(), isSaved -> {
            isPlaceSaved = isSaved;
            updateSaveButton();
        });
    }

    /**
     * Toggle save/unsave place
     */
    private void toggleSavePlace() {
        if (isPlaceSaved) {
            placeViewModel.removePlace(placeId);
            Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            if (currentPlace != null) {
                currentPlace.setSaved(true);
                placeViewModel.savePlace(currentPlace);
                Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        }
        isPlaceSaved = !isPlaceSaved;
        updateSaveButton();
    }

    /**
     * Update save button appearance
     */
    private void updateSaveButton() {
        if (isPlaceSaved) {
            btnSave.setText("Unsave");
        } else {
            btnSave.setText("Save");
        }
    }

    /**
     * Open directions to the place in Google Maps
     */
    private void openDirections() {
        if (currentPlace != null) {
            Uri gmmIntentUri = Uri.parse("geo:" + currentPlace.getLatitude() + "," +
                    currentPlace.getLongitude() + "?q=" + Uri.encode(currentPlace.getName()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // If Google Maps is not installed, open in browser
                Uri browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" +
                        Uri.encode(currentPlace.getLatitude() + "," + currentPlace.getLongitude()));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
                startActivity(browserIntent);
            }
        }
    }

    /**
     * Share the place
     */
    private void sharePlace() {
        if (currentPlace != null) {
            String shareText = currentPlace.getName() + "\n" +
                    "Address: " + currentPlace.getFormattedAddress() + "\n";

            if (currentPlace.getWebsite() != null && !currentPlace.getWebsite().isEmpty()) {
                shareText += "Website: " + currentPlace.getWebsite() + "\n";
            }

            shareText += "View in RoamMate app";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out " + currentPlace.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
}


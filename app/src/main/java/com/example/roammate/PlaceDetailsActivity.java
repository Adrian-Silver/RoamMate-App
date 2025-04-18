package com.example.roammate;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.roammate.viewmodel.PlaceViewModel;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_PLACE_ID = "place_id";
    private PlaceViewModel placeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_details);

        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewModel
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);

        // Get place ID from intent
        String placeId = getIntent().getStringExtra(EXTRA_PLACE_ID);

        if (placeId != null && savedInstanceState == null) {
            // Create and add the details fragment
            PlaceDetailsFragment fragment = PlaceDetailsFragment.newInstance(placeId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
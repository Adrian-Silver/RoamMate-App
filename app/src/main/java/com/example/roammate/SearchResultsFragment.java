package com.example.roammate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.roammate.adapters.SearchResultAdapter;

public class SearchResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyView;
    private SearchResultAdapter adapter;

    private String category = "all";
    private String currentQuery = "";

    public void setCategory(String category) {
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Initialize views
        recyclerView = root.findViewById(R.id.recycler_view);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        progressBar = root.findViewById(R.id.progress_bar);
        emptyView = root.findViewById(R.id.empty_view);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchResultAdapter(getContext());
        recyclerView.setAdapter(adapter);

        // Setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Refresh results with the same query
            if (!currentQuery.isEmpty()) {
                performSearch(currentQuery);
            }
        });

        return root;
    }

    public void performSearch(String query) {
        if (query.isEmpty()) {
            return;
        }

        currentQuery = query;

        // Show loading state
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        // Here we would typically call a repository or ViewModel method to fetch search results
        // For now, we'll simulate a network call with a delay

        // Simulating API call delay
        getView().postDelayed(() -> {
            // Hide loading state
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            // This is where we would process the API response and update the adapter
            // For demonstration, we'll just show the recycler view with mock data

            // If we received results
            boolean hasResults = true; // This would be based on actual API response

            if (hasResults) {
                // The actual data would come from the API
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);

                // This would use real data from the API
                adapter.setSearchResults(getMockResults());
            } else {
                // No results case
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        }, 1500); // Simulate network delay
    }

    // This is just a placeholder for mock data
    // In a real app, this data would come from the network or database
    private SearchResult[] getMockResults() {
        // Return different mock data depending on the category
        return new SearchResult[]{
                // Sample data for demonstration
                new SearchResult("1", "Sample Place 1", category,
                        "Sample Address 1", 4.5f, 1.2f, ""),
                new SearchResult("2", "Sample Place 2", category,
                        "Sample Address 2", 3.5f, 2.3f, ""),
                new SearchResult("3", "Sample Place 3", category,
                        "Sample Address 3", 5.0f, 0.8f, "")
        };
    }

    // Inner class for search result model
    public static class SearchResult {
        private String id;
        private String name;
        private String category;
        private String address;
        private float rating;
        private float distance;
        private String imageUrl;

        public SearchResult(String id, String name, String category, String address,
                            float rating, float distance, String imageUrl) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.address = address;
            this.rating = rating;
            this.distance = distance;
            this.imageUrl = imageUrl;
        }

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getAddress() { return address; }
        public float getRating() { return rating; }
        public float getDistance() { return distance; }
        public String getImageUrl() { return imageUrl; }
    }
}

package com.example.roammate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import com.example.roammate.adapters.SearchPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SearchFragment extends Fragment {

    private Toolbar toolbar;
//    private EditText searchEditText;
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private SearchPagerAdapter searchPagerAdapter;

    private String searchQuery = "";
    private String initialCategory = null;

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

        // Set up toolbar
        toolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        // Set up search SearchView
        setupSearchView();

        // Set up ViewPager with TabLayout
        setupViewPagerWithTabs();

        return root;
    }

    private void setupSearchView() {
        // Configuration
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.requestFocus();

        // Query Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: Optionally implement real-time search suggestions
                return false;
            }
        });

    }

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

    private void performSearch(String query) {
        if (query.isEmpty()) {
            return;
        }

        searchQuery = query;

        // Pass search query to all tabs
        for (int i = 0; i < searchPagerAdapter.getItemCount(); i++) {
//            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + i);
            Fragment fragment = getChildFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
            if (fragment instanceof SearchResultsFragment) {
                ((SearchResultsFragment) fragment).performSearch(query);
            }
        }
    }
}

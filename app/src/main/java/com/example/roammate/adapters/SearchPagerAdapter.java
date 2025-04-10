package com.example.roammate.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.roammate.SearchResultsFragment;

public class SearchPagerAdapter extends FragmentStateAdapter {

    public SearchPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create tab fragments with appropriate category
        SearchResultsFragment fragment = new SearchResultsFragment();

        switch (position) {
            case 0:
                fragment.setCategory("all");
                break;
            case 1:
                fragment.setCategory("attractions");
                break;
            case 2:
                fragment.setCategory("hotels");
                break;
            case 3:
                fragment.setCategory("restaurants");
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}

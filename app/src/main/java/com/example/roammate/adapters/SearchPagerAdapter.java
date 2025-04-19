package com.example.roammate.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.roammate.SearchResultsFragment;

//public class SearchPagerAdapter extends FragmentStateAdapter {
//
//    public SearchPagerAdapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        // Create tab fragments with appropriate category
//        SearchResultsFragment fragment = new SearchResultsFragment();
//
//        switch (position) {
//            case 0:
//                fragment.setCategory("all");
//                break;
//            case 1:
//                fragment.setCategory("attractions");
//                break;
//            case 2:
//                fragment.setCategory("hotels");
//                break;
//            case 3:
//                fragment.setCategory("restaurants");
//                break;
//        }
//
//        return fragment;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 4; // Number of tabs
//    }
//}

public class SearchPagerAdapter extends FragmentStateAdapter {

    // Store created fragments for easy access
    private final SearchResultsFragment[] fragments = new SearchResultsFragment[4];

    public SearchPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);

        // Pre-create fragments with appropriate categories
        fragments[0] = new SearchResultsFragment();
        fragments[0].setCategory("all");

        fragments[1] = new SearchResultsFragment();
        fragments[1].setCategory("attractions");

        fragments[2] = new SearchResultsFragment();
        fragments[2].setCategory("hotels");

        fragments[3] = new SearchResultsFragment();
        fragments[3].setCategory("restaurants");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return the pre-created fragment
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }

    /**
     * Get the fragment at the specified position
     */
    public SearchResultsFragment getFragment(int position) {
        if (position >= 0 && position < fragments.length) {
            return fragments[position];
        }
        return null;
    }
}

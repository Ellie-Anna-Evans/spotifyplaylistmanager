package com.example.spotifyplaylistmanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.spotifyplaylistmanager.fragments.TopArtistFragment;
import com.example.spotifyplaylistmanager.fragments.TopTrackFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TopTrackFragment();
            case 1:
                return new TopArtistFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Top Tracks";
            case 1:
                return "Top Artists";
            default:
                return null;
        }

    }
}

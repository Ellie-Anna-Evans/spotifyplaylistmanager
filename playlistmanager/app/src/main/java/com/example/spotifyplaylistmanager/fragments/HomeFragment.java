package com.example.spotifyplaylistmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.spotifyplaylistmanager.MainActivity;
import com.example.spotifyplaylistmanager.PagerAdapter;
import com.example.spotifyplaylistmanager.R;
import com.example.spotifyplaylistmanager.SettingsActivity;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mViewPager = view.findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = getActivity().findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //MainActivity activity = (MainActivity)getActivity();

        //activity.loadArtistsTracks();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Spotify Playlist Manager");

        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_home,menu);
        final MenuItem searchItem = menu.findItem(R.id.setting);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


package com.example.spotifyplaylistmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyplaylistmanager.MainActivity;
import com.example.spotifyplaylistmanager.R;
import com.example.spotifyplaylistmanager.TopArtistAdapter;
import com.example.spotifyplaylistmanager.data.TopArtist;
import com.spotify.protocol.types.Artist;

import java.util.ArrayList;
import java.util.List;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;

public class TopArtistFragment extends Fragment implements TopArtistAdapter.OnTopArtistClickListener{

    private TopArtistAdapter mArtistAdapter;
    private RecyclerView mArtistRV;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_top_artists, container, false);
       // getActivity().setTitle("Spotify Playlist Manager");

        mArtistRV = rootView.findViewById(R.id.rv_top_artists);
        mArtistRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainActivity = (MainActivity)getActivity();

        mArtistAdapter = new TopArtistAdapter(this);
        mArtistRV.setAdapter(mArtistAdapter);
        mArtistRV.setHasFixedSize(true);

        mainActivity.mTopViewModel.getTopArtists().observe(this, new Observer<ArrayList<TopArtist>>() {
            @Override
            public void onChanged(ArrayList<TopArtist> artists) {
                mArtistAdapter.updateArtists(artists);
            }
        });

        return rootView;
    }

    @Override
    public void onArtistClick(TopArtist artist) {

    }
}

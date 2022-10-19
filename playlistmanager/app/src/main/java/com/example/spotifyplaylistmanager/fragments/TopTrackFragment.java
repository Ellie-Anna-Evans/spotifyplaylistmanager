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
import com.example.spotifyplaylistmanager.TopTrackAdapter;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;
import java.util.List;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;

public class TopTrackFragment extends Fragment implements TopTrackAdapter.OnTopTrackClickListener {

    private TopTrackAdapter mTopTrackAdapter;
    private RecyclerView mTrackRV;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        //getActivity().setTitle("Spotify Playlist Manager");

        mTrackRV = rootView.findViewById(R.id.rv_top_tracks);
        mTrackRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainActivity = (MainActivity)getActivity();

        mTopTrackAdapter = new TopTrackAdapter(this);
        mTrackRV.setAdapter(mTopTrackAdapter);
        mTrackRV.setHasFixedSize(true);

        mainActivity.mTopViewModel.getTopTracks().observe(this, new Observer<ArrayList<Track>>() {
            @Override
            public void onChanged(ArrayList<Track> tracks) {
                mTopTrackAdapter.updateTracks(tracks);
            }
        });

        return rootView;
    }

    @Override
    public void onTrackClick(Track track) {

    }
}

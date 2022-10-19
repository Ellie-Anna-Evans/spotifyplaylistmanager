package com.example.spotifyplaylistmanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.spotifyplaylistmanager.data.TopArtist;
import com.example.spotifyplaylistmanager.data.TopRepository;
import com.spotify.protocol.types.Artist;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;

public class TopViewModel extends ViewModel {

    private LiveData<ArrayList<TopArtist>> mTopArtists;
    private LiveData<ArrayList<Track>> mTopTracks;

    private TopRepository mRepository;

    public TopViewModel(){
        mRepository = new TopRepository();
        mTopArtists = mRepository.getTopArtists();
        mTopTracks = mRepository.getTopTracks();
    }

    public void loadTopArtists(String ACCESS_TOKEN){
        mRepository.loadTopArtists(ACCESS_TOKEN);
    }
    public void loadTopTracks(String ACCESS_TOKEN){ mRepository.loadTopTracks(ACCESS_TOKEN); }

    public LiveData<ArrayList<TopArtist>> getTopArtists(){ return mTopArtists; }
    public LiveData<ArrayList<Track>> getTopTracks(){ return mTopTracks; }
}

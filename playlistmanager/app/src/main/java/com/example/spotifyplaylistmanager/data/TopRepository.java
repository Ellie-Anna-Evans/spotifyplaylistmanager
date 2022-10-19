package com.example.spotifyplaylistmanager.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifyplaylistmanager.tasks.LoadTracksTask;
import com.example.spotifyplaylistmanager.tasks.LoadTopArtistTask;
import com.spotify.protocol.types.Artist;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;

public class TopRepository implements LoadTopArtistTask.Callback, LoadTracksTask.AsyncCallback {

    private MutableLiveData<ArrayList<TopArtist>> mTopArtists;
    private MutableLiveData<ArrayList<Track>> mTopTracks;


    public TopRepository(){
        mTopTracks = new MutableLiveData<>();
        mTopTracks.setValue(null);

        mTopArtists = new MutableLiveData<>();
        mTopArtists.setValue(null);
    }

    public void loadTopArtists(String ACCESS_TOKEN){
        mTopArtists.setValue(null);
        String URL = "https://api.spotify.com/v1/me/top/artists?limit=20&offset=0";
        Log.d("URL: ", URL);

        new LoadTopArtistTask(URL, this, ACCESS_TOKEN).execute();
    }

    public void loadTopTracks(String ACCESS_TOKEN){
        mTopArtists.setValue(null);
        String URL = "https://api.spotify.com/v1/me/top/tracks";
        Log.d("URL: ", URL);

        new LoadTracksTask(URL, this, ACCESS_TOKEN).execute();
    }

    public MutableLiveData<ArrayList<TopArtist>> getTopArtists() {
        return mTopArtists;
    }
    public MutableLiveData<ArrayList<Track>> getTopTracks() { return mTopTracks; }


    @Override
    public void onTracksLoadFinished(ArrayList<Track> tracks) {
        mTopTracks.setValue(tracks);
    }

    @Override
    public void onArtistLoadFinished(ArrayList<TopArtist> artists) {
        mTopArtists.setValue(artists);
    }
}

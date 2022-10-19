package com.example.spotifyplaylistmanager.tasks;

import android.util.Log;

import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;

public class LoadTracksTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onTracksLoadFinished(ArrayList<Track> playlistTrackItems);
    }

    private String mUrl;
    private LoadTracksTask.AsyncCallback mCallback;
    private String ACCESS_TOKEN;

    public LoadTracksTask(String url, LoadTracksTask.AsyncCallback callback, String token){
        mUrl = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String userPlaylistsJSON = null;
        Log.d("Attempting http in: ", "LoadTracksTask");
        try {
            userPlaylistsJSON = NetworkUtils.doHTTPGet(mUrl, ACCESS_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d( "LoadTracksTask: ",userPlaylistsJSON);
        return userPlaylistsJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<Track> userTracks = null;
        Log.d("Prinnnnnnnnnnt:", "test" );
        Log.d("S was not null Tracks:", s);
        Log.d("s != null: " , String.valueOf(s != null));
        if(s != null) {
            userTracks = SpotifyUtils.parseTrackResults(s);
            Log.d("S was not null Tracks:", s);
        }

        mCallback.onTracksLoadFinished(userTracks);
    }

}

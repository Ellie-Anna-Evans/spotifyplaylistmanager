package com.example.spotifyplaylistmanager;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.util.ArrayList;

public class LoadTracksSearchTask extends AsyncTask<Void, Void, String> {

    private SearchAdapter mSearchAdapter;

    public interface AsyncCallback {
        void onTracksLoadFinished(ArrayList<Track> playlistTrackItems);
    }

    private String mUrl;
    private LoadTracksSearchTask.AsyncCallback mCallback;
    private String ACCESS_TOKEN;

    public LoadTracksSearchTask(String url, LoadTracksSearchTask.AsyncCallback callback, String token){
        mUrl = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String userPlaylistsJSON = null;
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
        Log.d( "onPostExecute: s",s);
        if(s != null){
            userTracks = SpotifyUtils.parseTrackSearchResult(s);
            Log.d("S was not null:", s);
        }
        mCallback.onTracksLoadFinished(userTracks);
    }

}

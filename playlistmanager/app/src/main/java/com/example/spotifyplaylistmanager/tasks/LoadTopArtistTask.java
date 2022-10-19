package com.example.spotifyplaylistmanager.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.data.TopArtist;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.spotify.protocol.types.Artist;

import java.io.IOException;
import java.util.ArrayList;

public class LoadTopArtistTask extends AsyncTask<Void, Void, String> {

    private String mURL;
    private Callback mCallback;
    private String ACCESS_TOKEN;

    public interface Callback{
        void onArtistLoadFinished(ArrayList<TopArtist> artists);
    }

    public LoadTopArtistTask(String url, Callback callback, String token){
        mURL = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String results = null;
            try {
                results = NetworkUtils.doHTTPGet(mURL, ACCESS_TOKEN);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return results;
    }

    @Override
    protected void onPostExecute(String s){
        ArrayList<TopArtist> results = null;
        Log.d("S was not null in Artist:", s);
        if (s != null) {
//            results = SpotifyUtils.parseArtistResults(s);
            Log.d("S was not null in Artist:", s);
        }
        mCallback.onArtistLoadFinished(results);
    }
}

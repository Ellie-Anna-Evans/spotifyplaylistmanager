package com.example.spotifyplaylistmanager.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;

public class LoadPlaylistsTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onPlaylistsLoadFinished(ArrayList<SpotifyPlaylist> userPlaylistItems);
    }

    private String mUrl;
    private AsyncCallback mCallback;
    private String ACCESS_TOKEN;

    public LoadPlaylistsTask(String url, AsyncCallback callback, String token){
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

        return userPlaylistsJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<SpotifyPlaylist> userPlaylists = null;
        if(s != null){
            userPlaylists = SpotifyUtils.parsePlaylistResults(s);
            Log.d("S was not null:", s);
        }
        mCallback.onPlaylistsLoadFinished(userPlaylists);
    }


}

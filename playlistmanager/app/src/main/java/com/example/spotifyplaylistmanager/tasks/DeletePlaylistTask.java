package com.example.spotifyplaylistmanager.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.util.ArrayList;

public class DeletePlaylistTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onDeletePlaylistFinished(String name);
    }

    private String mUrl;
    private AsyncCallback mCallback;
    private String ACCESS_TOKEN;
    private String playlistUri;
    private String playlistName;

    public DeletePlaylistTask(String url, AsyncCallback callback, String token, SpotifyPlaylist playlist){
        mUrl = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
        playlistUri = playlist.uri;
        playlistName = playlist.name;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String addPlaylistResultJson = null;
        String data = null;
        try {
            addPlaylistResultJson = NetworkUtils.doHTTPDelete(mUrl, ACCESS_TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addPlaylistResultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Result of delete playlist", s);

        mCallback.onDeletePlaylistFinished(playlistName);
    }


}

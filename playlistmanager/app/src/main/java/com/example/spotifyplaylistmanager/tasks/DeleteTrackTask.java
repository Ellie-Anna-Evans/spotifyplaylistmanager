package com.example.spotifyplaylistmanager.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.util.ArrayList;

public class DeleteTrackTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onDeleteTrackFinished(String name);
    }

    private String mUrl;
    private AsyncCallback mCallback;
    private String ACCESS_TOKEN;
    private String trackUri;
    private String trackName;

    public DeleteTrackTask(String url, AsyncCallback callback, String token, Track track){
        mUrl = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
        trackUri = track.uri;
        trackName = track.name;

    }

    @Override
    protected String doInBackground(Void... voids) {
        String addPlaylistResultJson = null;

        String data = "{\"tracks\": [ {\"uri\":\"" + trackUri + "\"}] }";
        try {
            addPlaylistResultJson = NetworkUtils.doHTTPDelete(mUrl, ACCESS_TOKEN, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addPlaylistResultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Result of delete track", s);

        mCallback.onDeleteTrackFinished(trackName);
    }


}

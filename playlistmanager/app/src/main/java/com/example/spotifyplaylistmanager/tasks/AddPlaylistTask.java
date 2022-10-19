package com.example.spotifyplaylistmanager.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AddPlaylistTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onAddPlaylistFinished(String success);
    }

    private String mUrl;
    private AsyncCallback mCallback;
    private String ACCESS_TOKEN;
    private String playlistName;

    public AddPlaylistTask(String url, AsyncCallback callback, String token, String name){
        mUrl = url;
        mCallback = callback;
        ACCESS_TOKEN = token;
        playlistName = name;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String addPlaylistResultJson = null;

        String data = "{\"name\":\" " + playlistName + "\", \"public\":false}";
        try {
            addPlaylistResultJson = NetworkUtils.doHTTPPost(mUrl, ACCESS_TOKEN, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addPlaylistResultJson;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Result of add playlist", s);
        String responseResult= "FAILURE";
        // TODO IF response to adding playlist was affirmative, not 404
        if(s != null){
            // TODO toast playlist was created
            Log.d("S was not null:", s);
            responseResult = "SUCCESS";
        }
        mCallback.onAddPlaylistFinished(responseResult);
    }


}

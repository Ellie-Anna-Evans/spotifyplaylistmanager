package com.example.spotifyplaylistmanager.utils;

import android.net.Uri;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;



import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.data.TopArtist;
import com.google.gson.Gson;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Artist;

import java.util.ArrayList;
import java.util.Arrays;

public class SpotifyUtils {
    private static final String SPOTIFY_SEARCH_BASE_URL = "https://api.spotify.com/v1/search";
    private static final String SPOTIFY_SEARCH_QUERY_PARAM = "q";
    private static final String SPOTIFY_SEARCH_TYPE = "type";
    private static final String SPOTIFY_SEARCH_TYPE_VALUE = "track";


    public static String buildWeatherSearchURL(String result) {
        return Uri.parse(SPOTIFY_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(SPOTIFY_SEARCH_QUERY_PARAM, result)
                .appendQueryParameter(SPOTIFY_SEARCH_TYPE, SPOTIFY_SEARCH_TYPE_VALUE)
                .build()
                .toString();
    }


    static public class PlaylistResults {
        public SpotifyPlaylist[] items;
    }

    static public class TrackResults {
        public SpotifyTrack[] items;
    }

    static public class TrackSearchResults{
        public Tracks tracks;
    }
    static public class Tracks{
        Track[] items;
    }



    static public class SpotifyTrack {
        Track track;
    }

    static public class ArtistResults {
        public TopArtist[] items;
    }

    public static ArrayList<SpotifyPlaylist>
    parsePlaylistResults(String json) {
        Gson gson = new Gson();
        PlaylistResults results =
                gson.fromJson(json, PlaylistResults.class);
        if (results != null & results.items != null) {
            return new ArrayList<SpotifyPlaylist>(Arrays.asList(results.items));
        } else {
            return null;
        }
    }

    public static ArrayList<Track>
    parseTrackResults(String json) {
        Gson gson = new Gson();
        TrackResults results =
                gson.fromJson(json, TrackResults.class);
        
        if (results != null && results.items != null) {
            ArrayList<Track> tracks = new ArrayList<>();
            for (int i = 0; i < results.items.length; i++) {
                tracks.add(results.items[i].track);
            }
            return tracks;
        } else {
            return null;
        }
    }

//    public static ArrayList<TopArtist>
//    parseArtistResults(String json){
//        Gson gson = new Gson();
//        ArtistResults results =
//                gson.fromJson(json, ArtistResults.class);
//
//        if(results != null){
//            ArrayList<TopArtist> artists = new ArrayList<>();
//            artists.addAll(Arrays.asList(results.items));
//            return artists;
//        } else {
//        }
//        }

    public static ArrayList<Track> parseTrackSearchResult (String json){
        Gson gson = new Gson();
        TrackSearchResults results =
                gson.fromJson(json, TrackSearchResults.class);
        Log.d("TAG", "parseTrackSearchResult: "+ results.toString());
        if(results != null && results.tracks.items != null ){
            Log.d("TAG", "parseTrackSearchResult: run here??");
            ArrayList<Track> tracks = new ArrayList<>();
            for (int i = 0; i < results.tracks.items.length; i++) {
                tracks.add(results.tracks.items[i]);
            }
            return tracks;
        }
        else {
            return null;
        }
    }
}

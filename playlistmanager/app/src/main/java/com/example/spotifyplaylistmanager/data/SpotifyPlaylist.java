package com.example.spotifyplaylistmanager.data;

import com.example.spotifyplaylistmanager.utils.SpotifyUtils;

import java.io.Serializable;

public class SpotifyPlaylist implements Serializable {

    public String id;
    public String name;
    public PlaylistTracks tracks;
    public String uri;
}

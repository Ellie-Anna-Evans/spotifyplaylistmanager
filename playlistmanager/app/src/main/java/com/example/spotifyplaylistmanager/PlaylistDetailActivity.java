package com.example.spotifyplaylistmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.tasks.DeleteTrackTask;
import com.example.spotifyplaylistmanager.tasks.LoadTracksTask;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;

import static com.example.spotifyplaylistmanager.MainActivity.mSpotifyAppRemote;

public class PlaylistDetailActivity extends AppCompatActivity implements
        LoadTracksTask.AsyncCallback,
        TrackAdapter.OnTrackClickListener,
        TrackAdapter.OnTrackLongClickListener,
        DeleteTrackTask.AsyncCallback {



    public static final String EXTRA_SPOTIFY_PLAYLIST = "SpotifyPlaylist";

    private SpotifyPlaylist mPlaylist;
    private ProgressBar mLoadingIndicatorPB;
    private RecyclerView mTracksRV;
    private TrackAdapter mTracksAdapter;

    private Button mPlayButton;
    private Button mShowInSpotifyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail);
        mTracksAdapter = new TrackAdapter(this, this);
        mTracksRV = findViewById(R.id.rv_playlist_tracks);
        mTracksRV.setLayoutManager(new LinearLayoutManager(this));
        mTracksRV.setAdapter(mTracksAdapter);
        mTracksRV.setVisibility(View.INVISIBLE);
        mPlayButton = findViewById(R.id.play_button);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);

        mShowInSpotifyButton = findViewById(R.id.show_in_spotify_button);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("mPlaylist.uri is: ", mPlaylist.uri);
                mSpotifyAppRemote.getPlayerApi().play(mPlaylist.uri);
//                mPlayButton.setBackgroundColor(Color.RED);
//                mPlayButton.setTextColor(Color.WHITE);
            }
        });

        mShowInSpotifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("mPlaylist.uri is: ", mPlaylist.uri);

                // TODO launch intent to Spotify playlist
                Intent spotifyIntent = new Intent(Intent.ACTION_VIEW);
                spotifyIntent.setData(Uri.parse(mPlaylist.uri));
                spotifyIntent.putExtra(Intent.EXTRA_REFERRER,
                        Uri.parse("android-app://" + "com.example.spotifyplaylistmanage"));
                startActivity(spotifyIntent);
            }
        });

        // TODO set loading animation while API call is being made


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_SPOTIFY_PLAYLIST)) {
            mPlaylist = (SpotifyPlaylist) intent.getSerializableExtra(EXTRA_SPOTIFY_PLAYLIST);
            this.setTitle(mPlaylist.name);
            Log.d("Tracks in detail activity: ", mPlaylist.tracks.toString());

            String trackUrl = mPlaylist.tracks.href;
            new LoadTracksTask(trackUrl, this, ACCESS_TOKEN).execute();

        }
    }

    @Override
    public void onTracksLoadFinished(ArrayList<Track> playlistTrackItems) {
        // TODO stop loading animation

        if(playlistTrackItems.size() > 0) {
            mTracksAdapter.setTracks(playlistTrackItems);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            mTracksRV.setVisibility(View.VISIBLE);
            Log.d("Finished loading tracks ", "ontracksload");
        }
        else{
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onTrackClicked(Track track, TextView trackTV) {
        // TODO edit controls????
        // Or a mode change, with a switch statement for mode?
        mSpotifyAppRemote.getPlayerApi().play(track.uri);

    }

    @Override
    public void onTrackLongClicked(Track track, TextView trackTV){
        // TODO show delete option
//        trackTV.setBackgroundColor(Color.RED);
        showDeleteTrackDialog(track);
    }

    @Override
    public void onDeleteTrackFinished(String name){
        // TODO show toast that track was deleted
        Toast.makeText(this,"Removed track: " + name, Toast.LENGTH_SHORT).show();
    }


    private void showDeleteTrackDialog(Track track) {
        String base_query_url = "https://api.spotify.com/v1/playlists/" + mPlaylist.id  + "/tracks";
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete track")
                .setMessage("Are you sure you want to remove '" + track.name + "' from this playlist?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            // TODO call RemoveTrackFromPlaylist Task here
                        new DeleteTrackTask(base_query_url, PlaylistDetailActivity.this, ACCESS_TOKEN, track).execute();
                        mTracksAdapter.removeTrack(track);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}

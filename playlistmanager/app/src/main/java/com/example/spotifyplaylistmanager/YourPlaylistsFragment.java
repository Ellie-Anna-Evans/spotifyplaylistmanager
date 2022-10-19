package com.example.spotifyplaylistmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.tasks.AddPlaylistTask;
import com.example.spotifyplaylistmanager.tasks.DeletePlaylistTask;
import com.example.spotifyplaylistmanager.tasks.LoadPlaylistsTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spotify.android.appremote.api.SpotifyAppRemote;


import java.util.ArrayList;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;
import static com.example.spotifyplaylistmanager.MainActivity.username;

public class YourPlaylistsFragment extends Fragment implements
        PlaylistAdapter.OnPlaylistClickListener,
        PlaylistAdapter.OnPlaylistLongClickListener,
        AddPlaylistTask.AsyncCallback,
        LoadPlaylistsTask.AsyncCallback,
        DeletePlaylistTask.AsyncCallback
        {

    private RecyclerView mPlaylistRecyclerView;
    private PlaylistAdapter mPlaylistAdapter;

    static ArrayList<SpotifyPlaylist> yourPlaylists;

    private ProgressBar mLoadingIndicatorP;

    String playlistName = null;

    private FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_your_playlists, container, false);
        getActivity().setTitle("Your Playlists");


        mPlaylistAdapter = new PlaylistAdapter(this, this);
        mPlaylistRecyclerView = rootView.findViewById(R.id.rv_your_playlists);
        mLoadingIndicatorP = rootView.findViewById(R.id.pb_loading_indicator_p);
        mLoadingIndicatorP.setVisibility(View.VISIBLE);

        String url = "https://api.spotify.com/v1/users/" + username + "/playlists";

        new LoadPlaylistsTask(url, this, ACCESS_TOKEN).execute();

        mPlaylistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlaylistRecyclerView.setVisibility(View.INVISIBLE);
        fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAddItemDialog();
            }
        });


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPlaylistClicked(SpotifyPlaylist playlist) {
        Log.d("Playlist was clicked: ", playlist.name);
        Intent intent = new Intent(getActivity(), PlaylistDetailActivity.class);
        intent.putExtra(PlaylistDetailActivity.EXTRA_SPOTIFY_PLAYLIST, playlist);
        startActivity(intent);
    }

    @Override
    public void onPlaylistLongClicked(SpotifyPlaylist playlist){
        showDeletePlaylistDialog(playlist);
    }

    // Callback for AddPlaylistTask
    @Override
    public void onAddPlaylistFinished(String success){
        // Show a toast to the user that their playlist was added
        Toast.makeText(getActivity(),"Added new playlist: " + playlistName, Toast.LENGTH_SHORT).show();
        
    }

    // Callback for deleteplaylisttask (unfollow)
    @Override
    public void onDeletePlaylistFinished(String name){
        Toast.makeText(getActivity(),"No longer following:  " + name, Toast.LENGTH_SHORT).show();
    }

    private void showAddItemDialog() {
        final EditText taskEditText = new EditText(getActivity());
        String base_query_url = "https://api.spotify.com/v1/users/" + username +"/playlists";
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Create new playlist")
                .setMessage("Enter a name for your playlist")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playlistName = String.valueOf(taskEditText.getText());
                        new AddPlaylistTask(base_query_url, YourPlaylistsFragment.this, ACCESS_TOKEN, playlistName).execute();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showDeletePlaylistDialog(SpotifyPlaylist playlist) {
        String base_query_url = "https://api.spotify.com/v1/playlists/" + playlist.id  + "/followers";
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Delete playlist")
                .setMessage("Are you sure you want to unfollow '" + playlist.name + "' ?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO call RemovePlaylist task here
                        new DeletePlaylistTask(base_query_url, YourPlaylistsFragment.this, ACCESS_TOKEN, playlist).execute();
                        mPlaylistAdapter.removePlaylist(playlist);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void onPlaylistsLoadFinished(ArrayList<SpotifyPlaylist> userPlaylistItems) {
        yourPlaylists = userPlaylistItems;
        mPlaylistAdapter.setPlaylists(yourPlaylists);
        mPlaylistRecyclerView.setAdapter(mPlaylistAdapter);
        mLoadingIndicatorP.setVisibility(View.INVISIBLE);
        mPlaylistRecyclerView.setVisibility(View.VISIBLE);
    }

}
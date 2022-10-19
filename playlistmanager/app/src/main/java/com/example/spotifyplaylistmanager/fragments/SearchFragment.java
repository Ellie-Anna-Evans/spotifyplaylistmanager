package com.example.spotifyplaylistmanager.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.spotifyplaylistmanager.LoadTracksSearchTask;
import com.example.spotifyplaylistmanager.R;
import com.example.spotifyplaylistmanager.data.PlaylistTracks;
import com.example.spotifyplaylistmanager.utils.NetworkUtils;
import com.example.spotifyplaylistmanager.utils.SpotifyUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;

public class SearchFragment extends Fragment implements LoadTracksSearchTask.AsyncCallback {
    private MaterialSearchView mMeterialSearchView;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle("Search");

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchMenu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }


        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i( "onQueryTextSubmit: ",query);
                    if(!TextUtils.isEmpty(query)){
                        doSpotifySearch(query);
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }




        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMenu:
                return false;

            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    private void doSpotifySearch(String searchQuery){
        url = SpotifyUtils.buildWeatherSearchURL(searchQuery);
        Log.d( "doSpotifySearch: ",url);
        new LoadTracksSearchTask(url,this,ACCESS_TOKEN).execute();

    }

    @Override
    public void onTracksLoadFinished(ArrayList<Track> playlistTrackItems) {
        Log.d("TAG", "onTracksLoadFinished: "+playlistTrackItems);

    }

    public class SpotifySearchTask extends AsyncTask<String,Void,String >{

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String searchResult = null;
            try {
                searchResult = NetworkUtils.doHTTPGet(url, ACCESS_TOKEN);
            } catch (IOException e){
                e.printStackTrace();
            }
            Log.d( "searchResult: ",searchResult);
            return searchResult;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<Track> searchResultsList = null;
            super.onPostExecute(s);
            if(s!=null){
               //searchResultsList = SpotifyUtils.parseTrackSearchResult(s);
            }
        }
    }
}












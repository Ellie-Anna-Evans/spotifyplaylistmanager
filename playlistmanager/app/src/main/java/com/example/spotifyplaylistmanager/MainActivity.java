package com.example.spotifyplaylistmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.spotifyplaylistmanager.fragments.HomeFragment;
import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.example.spotifyplaylistmanager.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Enter your client ID from the Spotify developer dashboard
    String client_id = "";

    // Enter your username uri
    public static String username = "";

    static final String REDIRECT_URI = "com.example.spotifyplaylistmanager://callback";

    // This is an arbitrary integer that's used to verify the login activity result
    int REQUEST_CODE = 1337;
    public static String ACCESS_TOKEN = null;


    public static SpotifyAppRemote mSpotifyAppRemote;

    public TopViewModel mTopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mTopViewModel = new ViewModelProvider(this).get(TopViewModel.class);

        authorizeSpotify();
    }

    //for nav bar at bottom
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_playlists:
                            selectedFragment = new YourPlaylistsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    // Spotify authorization achieved by connecting to Spotify client on the device
    private void authorizeSpotify() {


        AuthorizationRequest request =
                new AuthorizationRequest.Builder(
                        client_id,
                        AuthorizationResponse.Type.TOKEN,
                        REDIRECT_URI)
                        .setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private, streaming", "playlist-modify-private"})
                        .build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    ACCESS_TOKEN = response.getAccessToken();
                    break;
                case ERROR:
                    // TODO handle error response
                    Log.d("response.getError: ", response.getError());
                    Log.d("Error getting token", "TOKEN");
                    break;
                default:
                    // TODO handle other cases
            }
        }
    }


//    public void loadArtistsTracks(){
//        mTopViewModel.loadTopArtists(ACCESS_TOKEN);
//        mTopViewModel.loadTopTracks(ACCESS_TOKEN);
//    }


    @Override
    protected void onStart() {
        super.onStart();

        ConnectionParams connectionParams = new ConnectionParams.Builder(client_id)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);
                    }
                });
    }


}

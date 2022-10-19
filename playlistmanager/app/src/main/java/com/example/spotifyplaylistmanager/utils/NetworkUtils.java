package com.example.spotifyplaylistmanager.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {

    private static final OkHttpClient mHTTPClient = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json");

    public static String doHTTPGet(String url, String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHTTPPost(String url, String token, String payload) throws IOException {
        Log.d("In doHTTPPost, token is: ", token);

        RequestBody body = RequestBody.create(payload, JSON);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Log.d("Post request with", request.toString());
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHTTPDelete(String url, String token, String payload) throws IOException {
        Log.d("In doHTTPDelete, token is: ", token);

        RequestBody body = RequestBody.create(payload, JSON);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .delete(body)
                .build();

        if(payload == null){
           request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .delete()
                    .build();
        }


        Log.d("Post request with", request.toString());
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHTTPDelete(String url, String token) throws IOException {
        Log.d("In doHTTPDelete, token is: ", token);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .delete()
                .build();

        Log.d("Post request with", request.toString());
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

}

package com.example.spotifyplaylistmanager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.spotify.protocol.types.Artist;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;

import static com.example.spotifyplaylistmanager.MainActivity.ACCESS_TOKEN;
import static com.example.spotifyplaylistmanager.MainActivity.username;


public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {
    private ArrayList<Track> mTrackList;
    private OnTrackClickListener mTrackClickListener;
    private OnTrackLongClickListener mTrackLongClickListener;

    interface OnTrackClickListener{
        void onTrackClicked(Track track, TextView trackTV);
    }

    interface OnTrackLongClickListener{
        void onTrackLongClicked(Track track, TextView trackTV);
    }


    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.playlist_track_item, viewGroup,
                false);
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track track =
                mTrackList.get(position);
        Log.d("Track in vhbind is:", track.name);
        holder.bind(track);
    }


    public TrackAdapter(OnTrackClickListener clickListener, OnTrackLongClickListener longClickListener) {
        mTrackClickListener = clickListener;
        mTrackLongClickListener = longClickListener;
        mTrackList = new ArrayList<Track>();
    }

    public void setTracks(ArrayList<Track> tracks) {
        mTrackList = new ArrayList<>(tracks);
        notifyDataSetChanged();
    }

    public void removeTrack(Track track){
        mTrackList.remove(track);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mTrackTextView;
        private TextView mArtistTextView;

        public TrackViewHolder(View itemView) {
            super(itemView);
            mTrackTextView =
                    itemView.findViewById(R.id.tv_track_text);
            mArtistTextView = itemView.findViewById(R.id.tv_track_artist_text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v){
            Track trackItem = mTrackList.get(getAdapterPosition());

            mTrackClickListener.onTrackClicked(trackItem, mTrackTextView);
        }

        @Override
        public boolean onLongClick(View v){
            // TODO show delete menu option
            Track trackItem = mTrackList.get(getAdapterPosition());
            mTrackLongClickListener.onTrackLongClicked(trackItem, mTrackTextView);
            return true;
        }

        void bind(Track track) {
            Log.d("Track Object", track.toString());
            if(mTrackTextView != null && mArtistTextView != null && track != null) {
                mTrackTextView.setText(track.name);
                mArtistTextView.setText(track.artists.get(0).name);
                Log.d("Tracktext: ", track.name);
            }
        }

    }

}

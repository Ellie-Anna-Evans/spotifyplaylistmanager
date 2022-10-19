package com.example.spotifyplaylistmanager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyplaylistmanager.data.SpotifyPlaylist;
import com.spotify.protocol.types.Track;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private ArrayList<SpotifyPlaylist> mPlaylistList;
    private OnPlaylistClickListener mPlaylistClickListener;
    private OnPlaylistLongClickListener mPlaylistLongClickListener;

    interface OnPlaylistClickListener{
        void onPlaylistClicked(SpotifyPlaylist playlist);
    }

    interface OnPlaylistLongClickListener{
        void onPlaylistLongClicked(SpotifyPlaylist playlist);
    }


    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.your_playlists_list_item, viewGroup,
                false);
        PlaylistViewHolder viewHolder = new PlaylistViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        SpotifyPlaylist playlist =
                mPlaylistList.get(position);
        holder.bind(playlist);
    }


    public PlaylistAdapter(OnPlaylistClickListener clickListener, OnPlaylistLongClickListener longClickListener) {
        mPlaylistClickListener = clickListener;
        mPlaylistLongClickListener = longClickListener;
        mPlaylistList = new ArrayList<SpotifyPlaylist>();
    }

    public void setPlaylists(ArrayList<SpotifyPlaylist> playlists) {
        mPlaylistList = new ArrayList<>(playlists);
        notifyDataSetChanged();
    }

    public void removePlaylist(SpotifyPlaylist playlist){
        mPlaylistList.remove(playlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPlaylistList.size();
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mPlaylistTextView;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            mPlaylistTextView =
                    itemView.findViewById(R.id.tv_playlist_text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v){
            SpotifyPlaylist playlistItem = mPlaylistList.get(getAdapterPosition());

            mPlaylistClickListener.onPlaylistClicked(playlistItem);
        }

        public boolean onLongClick(View v){
            SpotifyPlaylist playlistItem = mPlaylistList.get(getAdapterPosition());
            mPlaylistLongClickListener.onPlaylistLongClicked(playlistItem);
            return true;
        }

        void bind(SpotifyPlaylist playlist) {
            mPlaylistTextView.setText(playlist.name);
        }


    }

}

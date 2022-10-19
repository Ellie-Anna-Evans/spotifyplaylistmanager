package com.example.spotifyplaylistmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyplaylistmanager.data.TopArtist;
import com.spotify.protocol.types.Artist;

import java.util.ArrayList;
import java.util.List;

public class TopArtistAdapter extends RecyclerView.Adapter<TopArtistAdapter.ArtistItemViewHolder>{

    private ArrayList<TopArtist> mTopArtists;
    private OnTopArtistClickListener mTopArtistClickListener;

    public interface OnTopArtistClickListener{
        void onArtistClick(TopArtist artist);
    }

    public TopArtistAdapter(OnTopArtistClickListener clickListener){
        mTopArtistClickListener = clickListener;
    }

    public void updateArtists(ArrayList<TopArtist> artists){
        mTopArtists = artists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArtistItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.top_artist, parent, false);
        return new ArtistItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistItemViewHolder holder, int position) {
        holder.bind(mTopArtists.get(position));
    }

    @Override
    public int getItemCount() {
        if(mTopArtists != null) {
            return mTopArtists.size();
        } else {
            return 0;
        }
    }

    class ArtistItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTopItemTV;

        public ArtistItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTopItemTV = itemView.findViewById(R.id.tv_top_artist);
            itemView.setOnClickListener(this);
        }

        public void bind(TopArtist artist){ mTopItemTV.setText(artist.name);}

        @Override
        public void onClick(View v) {
            TopArtist artist = mTopArtists.get(getAdapterPosition());
            mTopArtistClickListener.onArtistClick(artist);
        }
    }
}

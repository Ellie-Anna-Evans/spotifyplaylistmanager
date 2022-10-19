package com.example.spotifyplaylistmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spotify.protocol.types.Track;

import java.util.List;

public class TopTrackAdapter extends RecyclerView.Adapter<TopTrackAdapter.TrackItemViewHolder>{

    private List<Track> mTopTracks;
    private TopTrackAdapter.OnTopTrackClickListener mTopTrackClickListener;

    public interface OnTopTrackClickListener{
        void onTrackClick(Track track);
    }

    public TopTrackAdapter(TopTrackAdapter.OnTopTrackClickListener clickListener){
        mTopTrackClickListener = clickListener;
    }

    public void updateTracks(List<Track> tracks){
        mTopTracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopTrackAdapter.TrackItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.top_artist, parent, false);
        return new TopTrackAdapter.TrackItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTrackAdapter.TrackItemViewHolder holder, int position) {
        holder.bind(mTopTracks.get(position));
    }

    @Override
    public int getItemCount() {
        if(mTopTracks != null) {
            return mTopTracks.size();
        } else {
            return 0;
        }
    }

    class TrackItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTopItemTV;

        public TrackItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTopItemTV = itemView.findViewById(R.id.tv_top_track);
            itemView.setOnClickListener(this);
        }

        public void bind(Track track){ mTopItemTV.setText(track.name);}

        @Override
        public void onClick(View v) {
            Track track = mTopTracks.get(getAdapterPosition());
            mTopTrackClickListener.onTrackClick(track);
        }
    }
}

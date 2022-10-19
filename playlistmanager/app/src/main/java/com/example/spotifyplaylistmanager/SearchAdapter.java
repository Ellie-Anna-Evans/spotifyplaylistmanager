package com.example.spotifyplaylistmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spotify.protocol.types.Track;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder> {

    private OnSearchItemClickListener mOnSearhItemClickListener;
    private ArrayList<Track> mTrackData;
    private RecyclerView mSearchAdapter;

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_list_item,parent,false);

        mSearchAdapter = view.findViewById(R.id.rv_search_view);



        return new SearchItemViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {

        holder.bind(mTrackData.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public interface OnSearchItemClickListener{
            void onSearchItemClick(Track searchTrack);
    }

    public void updateSearchData(ArrayList<Track> trackData){
        mTrackData = trackData;
        notifyDataSetChanged();
    }

    class SearchItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mSearchTextView;

        public SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mSearchTextView = itemView.findViewById(R.id.tv_search_text);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mOnSearhItemClickListener.onSearchItemClick(
                            mTrackData.get(getAdapterPosition())
                    );
                }
            });

        }

        public void bind(Track trackData){
            mSearchTextView.setText(trackData.name);
        }
    }

}

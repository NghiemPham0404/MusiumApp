package com.example.musiumproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musiumproject.R;
import com.example.musiumproject.View.SongPlaying;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.util.Credentials;
import com.example.musiumproject.util.TrackPlayingType;
import com.example.musiumproject.viewmodels.TrackViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {
    @Setter
    List<Track> tracks;
    Context context;
    TrackPlayingType trackPlayingType;
    AdapterSize adapterSize;

    public enum AdapterSize{
        regular, small;
    }

    public TrackAdapter(Context context, TrackPlayingType trackPlayingType){
        this.context = context;
        this.trackPlayingType = trackPlayingType;
        this.adapterSize = AdapterSize.regular;
    }

    public TrackAdapter(Context context, TrackPlayingType trackPlayingType, AdapterSize adapterSize){
        this.context = context;
        this.trackPlayingType = trackPlayingType;
        this.adapterSize = adapterSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (adapterSize == AdapterSize.regular) {
            return 1;
        }
        return 0;
    }

    @androidx.annotation.NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_small, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        }
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull TrackViewHolder holder, int position) {
        int pos = position;
        Track track = tracks.get(position);
        holder.title.setText(track.getTitle());
        holder.artists.setText(track.getArtistStr());
        String trackImageUrl = Credentials.STORAGE_URL + Credentials.TRACK_IMG + track.getTrackImg();
        Glide.with(context).load(trackImageUrl).into(holder.cover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongPlaying.class);
                intent.putExtra("id", track.getId());
                intent.putExtra("index", pos);
                intent.putExtra("origin", track.getArtistStr());
                new TrackViewModel().choosePlayingTrackList(trackPlayingType);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(tracks != null){
            return tracks.size();
        }
        return 0;
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView title, artists;
        ShapeableImageView cover;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trackTitle);
            artists = itemView.findViewById(R.id.trackArtist);
            cover = itemView.findViewById(R.id.trackCover);
        }
    }
}

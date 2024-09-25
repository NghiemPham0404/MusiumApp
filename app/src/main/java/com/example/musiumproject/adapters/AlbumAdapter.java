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
import com.example.musiumproject.View.Playlist;
import com.example.musiumproject.models.Album;
import com.example.musiumproject.util.Credentials;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    Context context;
    @Setter
    List<Album> albums;

    public AlbumAdapter(Context context){
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumAdapter.AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull AlbumViewHolder holder, int position) {
        holder.albumTitle.setText(albums.get(position).getTitle());
        holder.albumArtist.setText(albums.get(position).getArtistName());
        Glide.with(context).load(Credentials.STORAGE_URL + Credentials.ALBUM + albums.get(position).getAlbumCover()).into(holder.albumCover);

        holder.itemView.setOnClickListener((l) -> {
            Intent intent = new Intent(context, Playlist.class);
            intent.putExtra("album_id", albums.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(albums != null){
            return albums.size();
        }
        return 0;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView albumCover;
        TextView albumTitle, albumArtist;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.albumCover);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumArtist = itemView.findViewById(R.id.albumArtist);
        }
    }
}

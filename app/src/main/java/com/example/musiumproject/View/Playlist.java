package com.example.musiumproject.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musiumproject.R;
import com.example.musiumproject.View.interfaceForView.ViewInit;
import com.example.musiumproject.adapters.TrackAdapter;
import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.util.Credentials;
import com.example.musiumproject.util.TrackPlayingType;
import com.example.musiumproject.viewmodels.AlbumViewModel;
import com.example.musiumproject.viewmodels.TrackViewModel;

public class Playlist extends AppCompatActivity implements ViewInit, View.OnClickListener {
    TextView playFromTxt, playListTitle, playlistArtist;
    ImageButton backBtn, imageButton10;
    ImageView playListImage;
    Button startPlayBtn;
    RecyclerView trackRecyclerVIew;


    AlbumViewModel albumViewModel;
    TrackAdapter trackAdapter;
    Album album;
    int id;
    String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        initComponents();

        id = getIntent().getIntExtra("album_id", 0);
        albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);
        observeAnyChange();
        initFeatures();
    }
    @Override
    public void initComponents(){
        playFromTxt = findViewById(R.id.playFromTxt);
        playListTitle = findViewById(R.id.playListTitle);
        playlistArtist = findViewById(R.id.playlistArtist);

        backBtn = findViewById(R.id.backBtn);
        imageButton10 = findViewById(R.id.imageButton10);

        playListImage = findViewById(R.id.playListImage);
        startPlayBtn = findViewById(R.id.startPlayBtn);
        trackRecyclerVIew = findViewById(R.id.trackRecyclerVIew);

        configureRecyclerView();
    }

    @Override
    public void initFeatures() {
        startPlayBtn.setOnClickListener(this);

        playlistArtist.setOnClickListener(this);

        albumViewModel.getAlbumById(id);
    }

    public void fillInfoIntoComponents(){
        String albumImageUrl = Credentials.STORAGE_URL + Credentials.ALBUM + album.getAlbumCover();
        Glide.with(this).load( albumImageUrl).into(playListImage);
        playListTitle.setText(album.getTitle());
        playlistArtist.setText(album.getArtistName());
    }

    @Override
    public void observeAnyChange() {
        if(albumViewModel!=null){
            albumViewModel.getLiveAlbum().observe(this, album -> {
                if(album!=null){
                    this.album = album;
                    trackAdapter.setTracks(album.getTracks());
                    trackAdapter.notifyDataSetChanged();
                    fillInfoIntoComponents();
                }
            });
        }
    }

    public void configureRecyclerView(){
        trackRecyclerVIew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trackAdapter = new TrackAdapter(this, TrackPlayingType.album, TrackAdapter.AdapterSize.small);
        trackRecyclerVIew.setAdapter(trackAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startPlayBtn.getId()){
            Intent intent = new Intent(getBaseContext(), SongPlaying.class);
            intent.putExtra("id", album.getTracks().get(0).getId());
            intent.putExtra("index", 0);
            intent.putExtra("origin", album.getTitle());
            new TrackViewModel().choosePlayingTrackList(TrackPlayingType.album);
            startActivity(intent);
        }else if(v.getId() == playlistArtist.getId()){
            Intent intent = new Intent(getBaseContext(), ArtistActivity.class);
            intent.putExtra("id", album.getArtist().getId());
            startActivity(intent);
        }
    }
}
package com.example.musiumproject.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.musiumproject.R;
import com.example.musiumproject.View.interfaceForView.ViewInit;
import com.example.musiumproject.adapters.AlbumAdapter;
import com.example.musiumproject.adapters.TrackAdapter;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.util.Credentials;
import com.example.musiumproject.util.TrackPlayingType;
import com.example.musiumproject.viewmodels.ArtistViewModel;

public class ArtistActivity extends AppCompatActivity implements ViewInit {

    // View
    RecyclerView trackRecyclerView, albumRecyclerView;
    TextView name, about, biography;
    ImageView artistImage;

    //Variable
    int id;
    Artist artist;
    ArtistViewModel artistViewModel;
    TrackAdapter trackAdapter;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        id = getIntent().getIntExtra("id", -1);
        artistViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        observeAnyChange();
        initComponents();
        initFeatures();
    }

    @Override
    public void initComponents() {
        trackRecyclerView = findViewById(R.id.trackRecyclerVIew);
        albumRecyclerView = findViewById(R.id.albumRecyclerVIew);

        artistImage = findViewById(R.id.artistImage);
        name = findViewById(R.id.artistName);
        about = findViewById(R.id.aboutArtist);
        biography = findViewById(R.id.artistBiography);

        configureRecyclerViews();
    }

    @Override
    public void initFeatures() {
        artistViewModel.getArtistById(id);
    }

    public void fillDataIntoView(){
        trackAdapter.setTracks(artist.getTracks());
        trackAdapter.notifyDataSetChanged();

        albumAdapter.setAlbums(artist.getAlbums());
        albumAdapter.notifyDataSetChanged();

        name.setText(artist.getName());
        String t = "About"+ artist.getName();
        about.setText(t);
        biography.setText(artist.getBiography());

        Glide.with(this).load(Credentials.STORAGE_URL + Credentials.ARTIST + artist.getImage()).into(artistImage);
    }

    @Override
    public void observeAnyChange() {
        if(artistViewModel != null){
            artistViewModel.getLiveArtist().observe(this, artist -> {
                if(artist != null){
                    this.artist = artist;
                    fillDataIntoView();
                }
            });
        }
    }

    public void configureRecyclerViews(){
        albumRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        albumAdapter = new AlbumAdapter(this);
        albumRecyclerView.setAdapter(albumAdapter);

        trackRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        trackAdapter = new TrackAdapter(this, TrackPlayingType.artist, TrackAdapter.AdapterSize.small);
        trackRecyclerView.setAdapter(trackAdapter);
    }
}
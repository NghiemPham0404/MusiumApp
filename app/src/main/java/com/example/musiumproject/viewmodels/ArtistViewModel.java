package com.example.musiumproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.repositories.ArtistRepository;

import java.util.List;

public class ArtistViewModel extends ViewModel {
    private ArtistRepository artistRepository;

    public ArtistViewModel() {
        artistRepository = ArtistRepository.getInstance();
    }

    public void getArtistById(int id) {
        artistRepository.getArtistById(id);
    }

    public LiveData<Artist> getLiveArtist(){
        return artistRepository.getLiveArtist();
    }

    public LiveData<List<Track>> getLiveTracks(){
        return  artistRepository.getLiveTracks();
    }

    public LiveData<List<Album>> getLiveAlbums(){
        return  artistRepository.getLiveAlbums();
    }
}

package com.example.musiumproject.repositories;

import androidx.lifecycle.LiveData;

import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.request.ArtistApiClient;

import java.util.List;

public class ArtistRepository {

    private static ArtistApiClient artistApiClient;
    private static ArtistRepository instance;

    public static ArtistRepository getInstance() {
        if (instance == null) {
            instance = new ArtistRepository();
        }
        return instance;
    }

    public ArtistRepository() {
        artistApiClient = ArtistApiClient.getInstance();
    }

    public void getArtistById(int id) {
        artistApiClient.getArtistById(id);
    }

    public LiveData<Artist> getLiveArtist(){
        return artistApiClient.getMArtist();
    }

    public LiveData<List<Track>> getLiveTracks(){
        return  artistApiClient.getMTracks();
    }

    public LiveData<List<Album>> getLiveAlbums(){
        return  artistApiClient.getMAlbums();
    }
}

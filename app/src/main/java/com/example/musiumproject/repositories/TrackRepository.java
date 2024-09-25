package com.example.musiumproject.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.musiumproject.models.Track;
import com.example.musiumproject.request.AlbumApiClient;
import com.example.musiumproject.request.ArtistApiClient;
import com.example.musiumproject.request.TrackApiClient;
import com.example.musiumproject.util.TrackPlayingType;

import java.util.List;

import lombok.Getter;

@Getter
public class TrackRepository {

    private static TrackRepository instance;
    private static TrackApiClient trackApiClient;
    private static AlbumApiClient albumApiClient;
    private static ArtistApiClient artistApiClient;

    int id, nPage, sPage;
    String query;
    TrackPlayingType trackPlayingType;

    public static TrackRepository getInstance(){
        if(instance == null){
            instance = new TrackRepository();
        }
        return instance;
    }

    private TrackRepository(){
          trackApiClient = TrackApiClient.getInstance();
          albumApiClient = AlbumApiClient.getInstance();
          artistApiClient = ArtistApiClient.getInstance();
    }

    public void choosePlayingTrackList(TrackPlayingType trackPlayingType){
        this.trackPlayingType = trackPlayingType;
    }

    public void loadNextPlayingTracks(){
        switch (trackPlayingType) {
            case newest:
                listNewestTracksNext();
                break;
            case search:
                searchTracksNext();
                break;
        }
    }

    public void getTrackById(int id){
        this.id = id;
        trackApiClient.getTrackById(id);
    }
    public void listNewestTracks(int page){
        this.nPage = page;
        trackApiClient.listNewestTracks(page);
    }
    public void listNewestTracksNext() {
            trackApiClient.listNewestTracks(++this.nPage);
    }

    public void searchTracks(String query, int page) {
        this.query = query;
        this.sPage = page;
        trackApiClient.searchTracks(query, page);
    }

    public void searchTracksNext() {
        trackApiClient.listNewestTracks(++this.sPage);
    }

    public LiveData<List<Track>> getLiveListTracks(){
        switch (trackPlayingType) {
            case newest:
               return trackApiClient.getMNewestTracks();
            case search:
                return trackApiClient.getMSearchTracks();
            case album:
               return albumApiClient.getMTracks();
            case artist:
                return artistApiClient.getMTracks();
            default:
                return new MutableLiveData<>();
        }
    }

    public LiveData<List<Track>> getLiveNewestTracks(){
        return trackApiClient.getMNewestTracks();
    }
    public LiveData<List<Track>> getLiveSearchTracks(){
        return trackApiClient.getMSearchTracks();
    }
    public LiveData<Track> getLiveTrack() {
        return trackApiClient.getMtrack();
    }
}

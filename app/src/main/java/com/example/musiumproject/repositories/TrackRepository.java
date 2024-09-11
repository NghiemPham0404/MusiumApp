package com.example.musiumproject.repositories;

import androidx.lifecycle.LiveData;

import com.example.musiumproject.models.Track;
import com.example.musiumproject.request.TrackApiClient;
import com.example.musiumproject.util.TrackPlayingType;

import java.util.List;

import lombok.Getter;

@Getter
public class TrackRepository {

    private static TrackRepository instance;
    private static TrackApiClient trackApiClient;

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
    }

    public void choosePlayingTrackList(TrackPlayingType trackPlayingType){
        trackApiClient.choosePlayingTrackList(trackPlayingType);
        this.trackPlayingType = trackPlayingType;
    }

    public void nextPlayingTrackList(){
        if(trackPlayingType == TrackPlayingType.newest){
          listNewestTracksNext();
        }else{
            searchTracksNext();
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
        if(trackPlayingType == TrackPlayingType.newest || trackPlayingType == TrackPlayingType.search){
            return trackApiClient.getMtracks();
        }else{
            return null;
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

package com.example.musiumproject.repositories;

import androidx.lifecycle.LiveData;

import com.example.musiumproject.models.Track;
import com.example.musiumproject.request.TrackApiClient;

import java.util.List;

import lombok.Getter;

@Getter
public class TrackRepository {

    private static TrackRepository instance;
    private static TrackApiClient trackApiClient;

    LiveData<List<Track>> liveListTracks;
    LiveData<List<Track>> liveSearchTracks;
    LiveData<Track> liveTrack;
    LiveData<Integer> livePage, liveTotalPage, liveSize;
    int id, page;
    String query;

    public static TrackRepository getInstance(){
        if(instance == null){
            instance = new TrackRepository();
        }
        return instance;
    }

    private TrackRepository(){
          trackApiClient = TrackApiClient.getInstance();
    }
    public void getTrackById(int id){
        this.id = id;
        trackApiClient.getTrackById(id);
    }
    public void listTracks(int page){
        this.page = page;
        trackApiClient.listTracks(page);
    }
    public void searchTracks(String query, int page) {
        this.query = query;
        this.page = page;
        trackApiClient.searchTracks(query, page);
    }
}

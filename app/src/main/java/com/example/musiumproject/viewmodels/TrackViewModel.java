package com.example.musiumproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.musiumproject.models.Track;
import com.example.musiumproject.repositories.TrackRepository;

import java.util.List;

public class TrackViewModel extends ViewModel {
    private TrackRepository trackRepository;

    public TrackViewModel(){
        trackRepository  = TrackRepository.getInstance();
    }

    public void getTrackById(int id){
        trackRepository.getTrackById(id);
    }

    public void listTracks(int page){
        trackRepository.listTracks(page);
    }

    public void searchTracks(String query, int page){
        trackRepository.searchTracks(query, page);
    }

    public LiveData<List<Track>> getLiveListTracks(){
        return trackRepository.getLiveListTracks();
    }

    public LiveData<List<Track>> getLiveSearchTracks(){
        return trackRepository.getLiveSearchTracks();
    }

    public LiveData<Track> getLiveTrack(){
        return trackRepository.getLiveTrack();
    }
}

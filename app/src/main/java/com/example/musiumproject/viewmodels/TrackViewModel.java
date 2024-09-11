package com.example.musiumproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.musiumproject.models.Track;
import com.example.musiumproject.repositories.TrackRepository;
import com.example.musiumproject.util.TrackPlayingType;

import java.util.List;

public class TrackViewModel extends ViewModel {
    private TrackRepository trackRepository;

    public TrackViewModel(){
        trackRepository  = TrackRepository.getInstance();
    }

    public void choosePlayingTrackList(TrackPlayingType trackPlayingType){
        trackRepository.choosePlayingTrackList(trackPlayingType);
    }

    public void getTrackById(int id){
        trackRepository.getTrackById(id);
    }

    public void listNewestTracks(int page){
        trackRepository.listNewestTracks(page);
    }

    public void listNewestTracksNext(){
        trackRepository.listNewestTracksNext();
    }

    public void searchTracks(String query, int page){
        trackRepository.searchTracks(query, page);
    }

    public LiveData<List<Track>> getNewestTracks(){
        return trackRepository.getLiveNewestTracks();
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

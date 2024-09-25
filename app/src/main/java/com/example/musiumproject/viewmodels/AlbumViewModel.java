package com.example.musiumproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.musiumproject.models.Album;
import com.example.musiumproject.repositories.AlbumRepository;

import java.util.List;

public class AlbumViewModel  extends ViewModel {
    private AlbumRepository albumRepository;

    public AlbumViewModel(){
        albumRepository = AlbumRepository.getInstance();
    }

    public void getAlbumById(int id){
        albumRepository.getAlbumById(id);
    }

    public void listNewestAlbums(int page){
        albumRepository.listNewestAlbums(page);
    }

    public void searchAlbums(String query, int page){
        albumRepository.searchAlbums(query, page);
    }

    public LiveData<List<Album>> getLiveNewestAlbums(){
        return albumRepository.getLiveNewestAlbums();
    }

    public LiveData<Album> getLiveAlbum(){
        return albumRepository.getLiveAlbum();
    }

    public LiveData<List<Album>> getLiveSearchAlbums(){
        return albumRepository.getLiveSearchAlbums();
    }
}

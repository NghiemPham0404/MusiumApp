package com.example.musiumproject.repositories;

import androidx.lifecycle.LiveData;

import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.request.AlbumApiClient;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AlbumRepository {
    private static AlbumRepository instance;
    AlbumApiClient albumApiClient;

    public static AlbumRepository getInstance() {
        if(instance == null){
            instance = new AlbumRepository();
        }
        return instance;
    }

    private AlbumRepository() {
        albumApiClient = AlbumApiClient.getInstance();
    }

    public void getAlbumById(int id){
        albumApiClient.getAlbumById(id);
    }

    public void listNewestAlbums(int page){
        albumApiClient.listNewestAlbums(page);
    }

    public void searchAlbums(String query, int page){
        albumApiClient.searchAlbums(query, page);
    }

    public LiveData<List<Album>> getLiveNewestAlbums(){
        return albumApiClient.getMNewestAlbums();
    }

    public LiveData<Album> getLiveAlbum(){
        return albumApiClient.getMAlbum();
    }

    public LiveData<List<Album>> getLiveSearchAlbums(){
        return albumApiClient.getMSearchAlbums();
    }

}

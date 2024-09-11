package com.example.musiumproject.request;

import androidx.lifecycle.MutableLiveData;

import com.example.musiumproject.AppExecutors;
import com.example.musiumproject.models.Album;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import lombok.Getter;

@Getter
public class AlbumApiClient {
    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<Album> album;
    private MutableLiveData<Integer> mPage, mTotalPage, mSize;

    ScheduledExecutorService networkIO;

    private static AlbumApiClient instance;

    public AlbumApiClient getInstance(){
        if(instance == null){
            instance = new AlbumApiClient();
        }
        return instance;
    }

    public AlbumApiClient() {
        albums = new MutableLiveData<>();
        album = new MutableLiveData<>();
        mPage = new MutableLiveData<>();
        networkIO = AppExecutors.getInstance().networkIO();
    }


}

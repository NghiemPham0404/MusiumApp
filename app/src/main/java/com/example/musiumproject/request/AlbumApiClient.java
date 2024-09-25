package com.example.musiumproject.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.musiumproject.AppExecutors;
import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.requests.services.MyService;
import com.example.musiumproject.responses.AlbumResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Response;

@Getter
public class AlbumApiClient {
    private MutableLiveData<List<Album>> mNewestAlbums, mSearchAlbums;
    private MutableLiveData<Album> mAlbum;
    private MutableLiveData<List<Track>> mTracks;
    private MutableLiveData<Integer> mPage, mTotalPage, mSize;

    ScheduledExecutorService networkIO;
    RetrieveAlbumRunnable retriveAlbumRunnable;
    private static AlbumApiClient instance;

    public static AlbumApiClient getInstance(){
        if(instance == null){
            instance = new AlbumApiClient();
        }
        return instance;
    }

    public AlbumApiClient() {
        mNewestAlbums = new MutableLiveData<>();
        mSearchAlbums = new MutableLiveData<>();
        mAlbum = new MutableLiveData<>();
        mTracks = new MutableLiveData<>();
        mPage = new MutableLiveData<>();
        networkIO = AppExecutors.getInstance().networkIO();
    }

    public void getAlbumById(int id){
        if(retriveAlbumRunnable != null){
            retriveAlbumRunnable = null;
        }
        retriveAlbumRunnable = new RetrieveAlbumRunnable(id);
        submitRetriveTrackRunnable(retriveAlbumRunnable);
    }

    public void listNewestAlbums(int page){
        if(retriveAlbumRunnable != null){
            retriveAlbumRunnable = null;
        }
        retriveAlbumRunnable = new RetrieveAlbumRunnable(null, page);
        submitRetriveTrackRunnable(retriveAlbumRunnable);
    }

    public void searchAlbums(String query, int page){
        if(retriveAlbumRunnable != null){
            retriveAlbumRunnable = null;
        }
        retriveAlbumRunnable = new RetrieveAlbumRunnable(query , page);
        submitRetriveTrackRunnable(retriveAlbumRunnable);
    };

    public void submitRetriveTrackRunnable(RetrieveAlbumRunnable retriveAlbumRunnable){
        final Future myHandler = networkIO.submit( retriveAlbumRunnable);
        networkIO.schedule(new Runnable() {
            @Override
            public void run() {
                retriveAlbumRunnable.cancelRequest();
                myHandler.cancel(true);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private class RetrieveAlbumRunnable implements Runnable{
        Integer id, page;
        String query;
        boolean isCancel;

        public RetrieveAlbumRunnable(int id){
            this.id = id;
            isCancel = false;
        }

        public RetrieveAlbumRunnable(String query,int page){
            this.page = page;
            this.query = query;
            isCancel = false;
        }

        @Override
        public void run() {
            Response response;
            try{
                if(id != null){
                    response = getAlbum().execute();
                    if(isCancel){
                        return ;
                    }else{
                        parseData(response);
//                        if(response.isSuccessful()){
//                            Album album = ((Response<Album>)response).body();
//                            List<Track> tracks = album.getTracks();
//                            parseData(album, tracks);
//                        }else{
//                            Log.e("retrive album error : ", response.errorBody().string());
//                        }
                    }
                }else{
                    if(query !=null){
                        response = searchAlbums().execute();
                        if(isCancel){
                            return;
                        }else{
                            parseData(response, mSearchAlbums);
//                            if(response.isSuccessful()){
//                                List<Album> albums  = ((Response<AlbumResponse>)response).body().getAlbums();
//                                parseData(albums, mSearchAlbums);
//                            }else{
//                                Log.e("retrive album error : ", response.errorBody().string());
//                            }
                        }
                    }else{
                        response = getAlbums().execute();
                        if(isCancel){
                            return;
                        }else{
                            parseData(response, mNewestAlbums);
//                            if(response.isSuccessful()){
//                                List<Album> albums  = ((Response<AlbumResponse>)response).body().getAlbums();
//                                for(Album album : albums){
//                                    Log.i("album title : ", album.getTitle());
//                                }
//                                parseData(albums, mNewestAlbums);
//                            }else{
//                                Log.e("retrive album error : ", response.errorBody().string());
//                            }
                        }
                    }
                }
            }catch (IOException e){
                Log.e("retrive album error : ", e.getMessage());
            }
        }

        private void parseData(Album album, List<Track>tracks){
            mAlbum.postValue(album);
            mTracks.postValue(tracks);
        }

        private void parseData(List<Album> albums, MutableLiveData<List<Album>> mAlbumsList){
            mAlbumsList.postValue(albums);
        }

        private void parseData(Response response){
            if(response.isSuccessful()){
                Album album = ((Response<Album>)response).body();
                List<Track> tracks = album.getTracks();
                parseData(album, tracks);
            }else{
                Log.e("retrive album error : ", response.errorBody().toString());
            }
        }
        public void parseData(Response response, MutableLiveData<List<Album>> mAlbumsList){
            if(response.isSuccessful()){
                List<Album> albums  = ((Response<AlbumResponse>)response).body().getAlbums();
                for(Album album : albums){
                    Log.i("album title : ", album.getTitle());
                }
                parseData(albums, mAlbumsList);
            }else{
                Log.e("retrive album error : ", response.errorBody().toString());
            }
        }

        Call<Album> getAlbum(){
            return MyService.getMusicAppApi().getAlbum(id);
        }

        Call<AlbumResponse> getAlbums(){
            return MyService.getMusicAppApi().getAlbums(page);
        }

        Call<AlbumResponse> searchAlbums(){
            return MyService.getMusicAppApi().searchAlbums(query, page);
        }

        public void cancelRequest() {
            this.isCancel = true;
        }
    }

}

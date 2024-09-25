package com.example.musiumproject.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.musiumproject.AppExecutors;
import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.requests.services.MyService;
import com.example.musiumproject.responses.ArtistResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Response;

@Getter
public class ArtistApiClient {

    private MutableLiveData<Artist> mArtist;
    private MutableLiveData<List<Artist>> mArtistList;
    private MutableLiveData<List<Track>> mTracks;
    private MutableLiveData<List<Album>> mAlbums;
    RetrieveArtistRunnable retrieveArtistRunnable;

    ScheduledExecutorService networkIO;

    private static ArtistApiClient instance;

    public static ArtistApiClient getInstance(){
        if(instance == null){
            instance = new ArtistApiClient();
        }
        return instance;
    }

    private ArtistApiClient(){
        mArtist = new MutableLiveData<>();
        mArtistList = new MutableLiveData<>();
        mTracks = new MutableLiveData<>();
        mAlbums = new MutableLiveData<>();
        networkIO = AppExecutors.getInstance().networkIO();
    }

    public void getArtistById(int id){
        if(retrieveArtistRunnable != null){
            retrieveArtistRunnable = null;
        }
        retrieveArtistRunnable  = new RetrieveArtistRunnable(id);
       submitRetriveTrackRunnable(retrieveArtistRunnable);
    }

    public void submitRetriveTrackRunnable(RetrieveArtistRunnable retrieveArtistRunnable){
        final Future myHandler = networkIO.submit( retrieveArtistRunnable);
        networkIO.schedule(new Runnable() {
            @Override
            public void run() {
                retrieveArtistRunnable.cancel();
                myHandler.cancel(true);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private class RetrieveArtistRunnable implements Runnable{
        Integer id, page;
        String query;
        boolean cancel = false;

        public RetrieveArtistRunnable(int id){
            this.id = id;
        }

        public RetrieveArtistRunnable(String query, int page){
            this.query = query;
            this.page = page;
        }

        @Override
        public void run() {
            Response response;
            try {
                if(id != null){
                    response = getArtist().execute();
                    parseData(response);
                }else{
                    response = searchArtists().execute();
                    parseData(response, mArtistList);
                }
            }catch (IOException e){
                Log.e("error retrive artist", e.getMessage());
            }
        }

        private void parseData(Response response){
            if(response.isSuccessful()){
                Artist artist = ((Response<Artist>)response).body();
                mAlbums.postValue(artist.getAlbums());
                mTracks.postValue(artist.getTracks());
                mArtist.postValue(artist);
            }else{
                Log.e("error retrive artist", response.errorBody().toString());
            }
        }

        private void parseData(Response response, MutableLiveData<List<Artist>> mutableLiveData){
            if(response.isSuccessful()){
                List<Artist> artists = ((Response<ArtistResponse>)response).body().getArtists();
                if(mArtistList.getValue()!=null){
                    List<Artist> currentArtists = mArtistList.getValue();
                    artists.addAll(currentArtists);
                }
            }else{
                Log.e("error retrive artist", response.errorBody().toString());
            }
        }

        private Call<ArtistResponse> searchArtists(){
            return MyService.getMusicAppApi().searchArtists(query, page);
        }

        private Call<Artist> getArtist(){
            return MyService.getMusicAppApi().getArtist(id);
        }

        private  void cancel(){
            cancel = true;
        }
    }

}

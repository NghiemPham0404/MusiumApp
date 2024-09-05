package com.example.musiumproject.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.musiumproject.AppExecutors;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.requests.services.MyService;
import com.example.musiumproject.responses.TrackResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Response;

@Getter
public class TrackApiClient {
    private MutableLiveData<List<Track>> mtracks;
    private MutableLiveData<List<Track>> mSearchTracks;
    private MutableLiveData<Track> mtrack;
    private MutableLiveData<Integer> mPage, mTotalPage, mSize;

    ScheduledExecutorService networkIO;
    RetriveTrackRunnable retriveTrackRunnableForTrack;

    private static TrackApiClient instance;

    public static TrackApiClient getInstance() {
        if (instance == null) {
            instance = new TrackApiClient();
        }
        return instance;
    }

    public TrackApiClient(){
        mtracks = new MutableLiveData<>();
        mtrack  = new MutableLiveData<>();
        mSearchTracks = new MutableLiveData<>();
        networkIO = AppExecutors.getInstance().networkIO();
        mPage = new MutableLiveData<>();
        mTotalPage = new MutableLiveData<>();
        mSize = new MutableLiveData<>();
    }

    public void getTrackById(int id){
        if(retriveTrackRunnableForTrack != null){
            retriveTrackRunnableForTrack = null;
        }
        retriveTrackRunnableForTrack = new RetriveTrackRunnable(id);
        submitRetriveTrackRunnable(retriveTrackRunnableForTrack);
    }

    public void listTracks(int page){
        if(retriveTrackRunnableForTrack != null){
            retriveTrackRunnableForTrack = null;
        }
        retriveTrackRunnableForTrack = new RetriveTrackRunnable(null ,page);
        submitRetriveTrackRunnable(retriveTrackRunnableForTrack);
    }

    public void searchTracks(String query, int page){
        if(retriveTrackRunnableForTrack != null){
            retriveTrackRunnableForTrack = null;
        }
        retriveTrackRunnableForTrack = new RetriveTrackRunnable(query ,page);
        submitRetriveTrackRunnable(retriveTrackRunnableForTrack);
    }

    public void submitRetriveTrackRunnable(RetriveTrackRunnable retriveTrackRunnable){
        final Future myHandler = networkIO.submit(retriveTrackRunnable);
        networkIO.schedule(new Runnable() {
            @Override
            public void run() {
                retriveTrackRunnable.cancelRequest();
                myHandler.cancel(true);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private class RetriveTrackRunnable implements Runnable{
        private Integer id;
        private Integer page;
        private String query;
        private boolean cancelRequest;

        public RetriveTrackRunnable (int id){
            this.id = id;
            cancelRequest = false;
        }

        public RetriveTrackRunnable(String query, int page){
            this.page = page;
            this.query = query;
            cancelRequest = false;
        }

        @Override
        public void run() {
            Response response;
                try {
                    if(id != null){
                        response =getTrack().execute();
                        if (cancelRequest) {
                            return;
                        }
                        if(response.isSuccessful()){
                            Track track = ((Response<Track>)response).body();
                            parseData(track);
                        }
                    }else{
                        if(query != null){
                            response = searchTracks().execute();
                        }else{
                            response = listTracks().execute();
                        }
                        if (cancelRequest) {
                            return;
                        }
                        if(response.isSuccessful()) {
                            TrackResponse trackResponse =  ((Response<TrackResponse>) response).body();
                            List<Track> tracks = trackResponse.getTracks();
                            int page = trackResponse.getPage();
                            int total_page = trackResponse.getTotal_page();
                            int size = trackResponse.getSize();
                            pasreListData(tracks, page, total_page, size);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        public void parseData(Track track){
            mtrack.postValue(track);
        }

        public void pasreListData(List<Track> tracks, int page, int totalPage, int size){
            mtracks.postValue(tracks);
            mPage.postValue(page);
            mTotalPage.postValue(totalPage);
            mSize.postValue(size);
        }

        Call<TrackResponse> listTracks(){
            return MyService.getMusicAppApi().getTracks(page);
        }

        Call<TrackResponse> searchTracks(){
            return MyService.getMusicAppApi().searchTracks(query, page);
        }

        Call<Track> getTrack(){
            return MyService.getMusicAppApi().getTrack(id);
        }

        public void cancelRequest(){
            this.cancelRequest = true;
            Log.i("cancel track request : ", "true");
        }
    }
}

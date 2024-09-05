package com.example.musiumproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.musiumproject.View.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Call<TrackResponse> trackCall = MyService.getMusicAppApi().getTracks(1);
//        trackCall.enqueue(new Callback<TrackResponse>() {
//            @Override
//            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
//                if(response.isSuccessful()){
//                    List<Track> tracks = response.body().getTracks();
//                    for(Track track : tracks){
//                        Log.i("Track : ", track.getTitle());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TrackResponse> call, Throwable t) {
//
//            }
//        });

        Intent intent = new Intent(getBaseContext(), Login.class);
        startActivity(intent);
    }
}
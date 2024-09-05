package com.example.musiumproject.requests.services;

import com.example.musiumproject.util.Credentials;
import com.example.musiumproject.util.MusicAppApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService {
    // the Retrofit class generates an implementation of the above interface
    private static final Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static final Retrofit retrofit = retrofitBuilder.build();

    public static MusicAppApi getMusicAppApi(){
        return retrofit.create(MusicAppApi.class);
    }
}

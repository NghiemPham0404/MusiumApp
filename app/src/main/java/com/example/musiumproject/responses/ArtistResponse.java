package com.example.musiumproject.responses;

import com.example.musiumproject.models.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class ArtistResponse extends MyResponse{
    @SerializedName(DATA)
    @Expose
    private List<Artist> artists;
}


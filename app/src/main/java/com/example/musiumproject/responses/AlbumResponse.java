package com.example.musiumproject.responses;

import com.example.musiumproject.models.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class AlbumResponse extends MyResponse{
    @SerializedName(DATA)
    @Expose
    private List<Album> albums;
}

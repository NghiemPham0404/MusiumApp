package com.example.musiumproject.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private Integer albumId;
    private String title;
    private String genre;
    private Date releaseDate;
    private String albumCover;
    private List<Track> tracks;
    private Artist artist;
}

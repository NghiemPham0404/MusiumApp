package com.example.musiumproject.models;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    protected Integer id;
    private String title;
    private Date releaseDate;
    private String trackImg;
    private String playLink;
    private Album album;
    private Set<Artist> artists;

    // artist_str is a string of artist names
    private String artistStr;
}

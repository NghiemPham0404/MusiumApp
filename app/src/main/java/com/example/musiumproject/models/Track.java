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
    protected Integer trackId;
    private String title;
    private String duration;
    private Date releaseDate;
    private String trackImg;
    private String playLink;
    private Album album;
    private Set<Artist> artists;
}

package com.example.musiumproject.models;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    private  Integer id;

    private String name;

    private String genre;

    private String image;

    private String biography;

    private List<Track> tracks;

    private List<Album> albums;
}

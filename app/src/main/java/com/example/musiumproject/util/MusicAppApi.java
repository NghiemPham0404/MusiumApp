package com.example.musiumproject.util;

import com.example.musiumproject.models.Album;
import com.example.musiumproject.models.Artist;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.responses.AlbumResponse;
import com.example.musiumproject.responses.ArtistResponse;
import com.example.musiumproject.responses.TrackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**  MUSIC APP RESTFUL API*/
public interface MusicAppApi {
    //TODO : ALBUM
    @GET(Credentials.ALBUM+"/all")
    Call<AlbumResponse> getAlbums(@Query("page") int page);

    @GET(Credentials.ALBUM+"/{album_id}")
    Call<Album> getAlbum(@Query("track_id") int trackId);

    @GET(Credentials.ALBUM+"/search")
    Call<AlbumResponse> searchAlbums(@Query("query") String query, @Query("page") int page);

    //TODO : ARTISTS
    @GET(Credentials.ARTIST+"/{artist_id}")
    Call<Artist> getArtist(@Query("artist_id") int trackId);

    @GET(Credentials.ARTIST+"/all")
    Call<ArtistResponse> getArtists(@Query("page") int page);

    @GET(Credentials.ARTIST+"/search")
    Call<ArtistResponse> searchArtists(@Query("query") String query, @Query("page") int page);


    //TODO : TRACKS
    @GET(Credentials.TRACK+"/{track_id}")
    Call<Track> getTrack(@Path("track_id") int trackId);

    @GET(Credentials.TRACK+"/all/newest")
    Call<TrackResponse> getTracks(@Query("page") int page);

    @GET(Credentials.TRACK+"/search")
    Call<TrackResponse> searchTracks(@Query("query") String query, @Query("page") int page);
}

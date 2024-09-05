package com.example.musiumproject.util;

public class Credentials {
    /** BASE URL OF MUSIC APP RESTFUL API*/
    public final static  String BASE_URL = "http://192.168.1.20:8080";
    public final static String STORAGE_URL = "http://192.168.1.20:81/msa";

    /** entity paths */
    public final static String TRACK = "/track";
    public final static String ARTIST = "/artist";
    public final static String ALBUM= "/album";

    /** image track path in storage url */
    public final static String TRACK_IMG = "/track_img";

    /** params paths */
    public final static String QUERY = "query";
    public final static String PAGE = "page";
}

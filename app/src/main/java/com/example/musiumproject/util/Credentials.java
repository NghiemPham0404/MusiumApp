package com.example.musiumproject.util;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Credentials {
    /** BASE URL OF MUSIC APP RESTFUL API*/
    public final static String IP = "192.168.1.20";
    public final static  String BASE_URL = String.format("http://%s:8080", IP) ;
    public final static String STORAGE_URL = String.format("http://%s:81/msa", IP) ; ;

    /** entity paths */
    public final static String TRACK = "/track";
    public final static String ARTIST = "/artist";
    public final static String ALBUM= "/album";

    /** image track path in storage url */
    public final static String TRACK_IMG = "/track_img";
}

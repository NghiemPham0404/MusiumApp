package com.example.musiumproject.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class MyResponse {
    protected static final String DATA = "data";
    protected static final String MESSAGE = "message";
    protected static final String SIZE = "total_results";
    protected static final String PAGE = "page";
    protected static final String TOTAL_PAGE = "total_page";
    protected static final String STATUS = "status";
    protected static final String HTTP_STATUS = "http_status";

    @SerializedName(PAGE)
    @Expose
    private int page;

    @SerializedName(TOTAL_PAGE)
    @Expose
    private int total_page;

    @SerializedName(SIZE)
    @Expose
    private int size;
}

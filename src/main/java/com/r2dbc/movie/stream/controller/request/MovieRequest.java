package com.r2dbc.movie.stream.controller.request;

import lombok.Data;


@Data
public class MovieRequest {
    private String title;
    private String genre;
    private String director;
    private int releaseYear;
    private String streamUrl;
}

package com.r2dbc.movie.stream.exception;

public interface GeneralException {

    String getContext();

    String getCode();

    String getMessage();
}

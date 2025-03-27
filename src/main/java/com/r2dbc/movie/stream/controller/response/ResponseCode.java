package com.r2dbc.movie.stream.controller.response;


import com.r2dbc.movie.stream.exception.BusinessException;

public enum ResponseCode {
    RECORD_NOT_FOUND("MV_1001", "Record not found!"),
    INTERNAL_SERVER_ERROR("MV_1002", "Internal Server Error");

    public final String code;
    public final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException asBusinessException() {
        return new BusinessException("movie_stream", code, message);
    }

}

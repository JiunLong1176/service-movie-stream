package com.r2dbc.movie.stream.controller.response;


import com.r2dbc.movie.stream.exception.BusinessException;

public enum ResponseCode {
    RECORD_NOT_FOUND("MV_1001", "Record not found!");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException asBusinessException() {
        return new BusinessException("movie", code, message);
    }

}

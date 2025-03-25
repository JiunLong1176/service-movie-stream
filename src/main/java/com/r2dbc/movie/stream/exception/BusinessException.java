package com.r2dbc.movie.stream.exception;

public class BusinessException extends RuntimeException implements GeneralException {
    private String context;
    private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String context, String code, String message) {
        super(message);
        this.context = context;
        this.code = code;
    }

    public String getContext() {
        return this.context;
    }

    public String getCode() {
        return this.code;
    }

    public BusinessException() {
    }
}

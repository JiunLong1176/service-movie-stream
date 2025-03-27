package com.r2dbc.movie.stream.exception;

import lombok.Getter;

@Getter
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

    public BusinessException() {
    }
}

package com.workintech.twitter.exceptions;

import org.springframework.http.HttpStatus;

public abstract class TwitterException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected TwitterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

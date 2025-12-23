package com.workintech.twitter.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends TwitterException {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}

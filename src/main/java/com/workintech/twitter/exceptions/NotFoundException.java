package com.workintech.twitter.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TwitterException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}


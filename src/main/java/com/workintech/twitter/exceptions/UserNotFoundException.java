package com.workintech.twitter.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends TwitterException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND); // 404
    }
}

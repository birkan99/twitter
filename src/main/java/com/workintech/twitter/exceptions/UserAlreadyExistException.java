package com.workintech.twitter.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException  extends TwitterException {
    public UserAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

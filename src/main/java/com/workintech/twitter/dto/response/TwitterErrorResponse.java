package com.workintech.twitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TwitterErrorResponse {

    private int status;
    private String message;
    private long timestamp;
    private LocalDateTime localDateTime;
}

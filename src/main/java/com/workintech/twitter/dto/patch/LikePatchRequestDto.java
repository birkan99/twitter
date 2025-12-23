package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record LikePatchRequestDto(
        @JsonProperty("tweet_id")
        Long tweetId,

        @JsonProperty("user_id")
        Long userId,


        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}

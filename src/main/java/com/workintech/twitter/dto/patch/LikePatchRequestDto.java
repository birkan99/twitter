package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record LikePatchRequestDto(
        @JsonProperty("tweet_id")
        Long tweetId,

        @JsonProperty("user_id")
        Long userId,


        @JsonProperty("created_at")
        OffsetDateTime createdAt
) {
}

package com.workintech.twitter.dto.patch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RetweetPatchRequestDto(
        @JsonProperty("original_tweet_id")
        Long originalTweetId,

        @JsonProperty("user_id")
        Long userId,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}

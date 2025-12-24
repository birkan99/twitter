package com.workintech.twitter.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("tweet_id") Long tweetId,
        @JsonProperty("user_id") Long userId
) {}

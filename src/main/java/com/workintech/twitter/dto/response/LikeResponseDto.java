package com.workintech.twitter.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("tweetId") Long tweetId,
        @JsonProperty("userId") Long userId
) {}

package com.workintech.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record RetweetResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("originalTweetId") Long originalTweetId,
        @JsonProperty("userId") Long userId,
        @JsonProperty("createdAt") OffsetDateTime createdAt
) {}

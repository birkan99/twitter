package com.workintech.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
public record CommentResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("content") String content,
        @JsonProperty("tweet_id") Long tweetId,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("created_at") OffsetDateTime createdAt
) {}
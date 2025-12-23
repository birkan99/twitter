package com.workintech.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record TweetResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("content") String content,
        @JsonProperty("created_at") OffsetDateTime createdAt,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("display_name") String displayName
) {}
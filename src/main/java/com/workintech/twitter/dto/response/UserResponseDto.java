package com.workintech.twitter.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record UserResponseDto(
        Long id,
        String email,
        @JsonProperty("display_name")
        String displayName,
        @JsonProperty("created_at")
        OffsetDateTime createdAt
) {}
